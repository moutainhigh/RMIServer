package com.hgsoft.unifiedInterface.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.Enum.TagIssueTypeEnum;
import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.Enum.UserStateInfoDealFlagEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagInfoCancelDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.dao.TagInfoMigrateDao;
import com.hgsoft.obu.dao.TagMaintainDao;
import com.hgsoft.obu.dao.TagReplaceDao;
import com.hgsoft.obu.dao.TagStopDao;
import com.hgsoft.obu.dao.TagTakeDetailDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.serviceInterface.ITagInfoService;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

/**
 * 电子标签同步接口
 * @author gaosiling
 * 2016年1月22日14:08:36
 */
@Service
public class ObuUnifiedInterfaceService {
	@Resource
	private TagReplaceDao tagReplaceDao;
	
	@Resource
	private TagStopDao tagStopDao;
	
	@Resource
	private TagInfoCancelDao tagInfoCancelDao;
	
	@Resource
	private TagMaintainDao tagMaintainDao;
	
	@Resource
	private TagInfoDao tagInfoDao;
	
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	
	@Resource
	private TagTakeDetailDao tagTakeDetailDao;
	
	@Resource
	private ITagInfoService tagInfoService;
	
	@Resource
	private TagInfoMigrateDao tagInfoMigrateDao;
	
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	
	@Resource
	private ICardObuService cardObuService;
	
	/*@Resource
	private UserInfoBaseListDao userInfoBaseListDao; */
	private static Logger logger = Logger.getLogger(ObuUnifiedInterfaceService.class.getName());
	/**
	 * 电子标签同步接口  type 1电子标签提货发行一次   2更换 3停用 4注销 5迁移 6删除	7维修 8电子标签先提货后发行  9维修返还客户 10电子标签回写
	 * @param unifiedParam
	 * @return
	 */
	public  boolean updateOrSaveObu(UnifiedParam unifiedParam){
		
		
		try {
			CarObuCardInfo carObuCardInfo = unifiedParam.getCarObuCardInfo();
			TagInfo tagInfo = unifiedParam.getTagInfo();
			/*TagInfo oldTagInfo = tagInfoDao.findByTagNo(tagInfo.getTagNo());*/
			if("1".equals(unifiedParam.getType())){
				/*saveObuServiceFlowRecord("1", null, tagInfo, unifiedParam.getMainAccountInfo());*/
				tagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				tagInfo.setWriteBackFlag("0");
				tagInfoDao.save(tagInfo);
				/*carObuCardInfoDao.save(carObuCardInfo);*/
				carObuCardInfoDao.updateTagNo(carObuCardInfo);
				return true;
			}else if("2".equals(unifiedParam.getType())){
				TagInfo oldTagInfo = tagInfoDao.findByTagNo(tagInfo.getTagNo());
				TagInfo newTagInfo = unifiedParam.getNewTagInfo();
				//重新绑定新的电子标签
				/*tagReplaceDao.updateCarObuCardInfo(unifiedParam.getNewTagInfo().getId(), carObuCardInfo);*/
				carObuCardInfo.setTagID(unifiedParam.getNewTagInfo().getId());
				// 将旧电子标签发行记录移除（即要删除旧的电子标签）至电子标签发行历史表，生成原因为“更换”
				// 保存到历史表
				TagInfoHis tagInfoHis = new TagInfoHis();
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis.setId(Long.parseLong(seq.toString()));
				tagInfoHis.setCreateReason("电子标签更换");
				//saveObuServiceFlowRecord("2", oldTagInfo, unifiedParam.getNewTagInfo(), null);
				newTagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				newTagInfo.setWriteBackFlag("0");
				tagInfoDao.save(newTagInfo);// 发行一条新的电子标签
				carObuCardInfoDao.updateTagNo(carObuCardInfo);
				oldTagInfo.setHisSeqID(tagInfoHis.getId());
				tagInfoHisDao.saveHis(tagInfoHis, oldTagInfo);
				// 原电子标签注销
				//tagInfoDao.delete(oldTagInfo.getId());
//				tagInfoCancelDao.updateTagInfo(oldTagInfo.getId(),tagInfoHis.getId());
				//2017/4/19 修改：需要将原电子标签的修正人信息update
				TagInfo updateOldTagInfo = new TagInfo();
				updateOldTagInfo.setTagState("4");//注销
				//updateOldTagInfo.setWriteBackFlag("0");
				updateOldTagInfo.setHisSeqID(tagInfoHis.getId());
				
				updateOldTagInfo.setId(oldTagInfo.getId());
				updateOldTagInfo.setCorrectTime(newTagInfo.getCorrectTime());//更新时间
				updateOldTagInfo.setCorrectOperID(newTagInfo.getOperID());
				updateOldTagInfo.setCorrectOperName(newTagInfo.getOperName());
				updateOldTagInfo.setCorrectOperNo(newTagInfo.getOperNo());
				updateOldTagInfo.setCorrectPlaceID(newTagInfo.getIssueplaceID());
				updateOldTagInfo.setCorrectPlaceName(newTagInfo.getPlaceName());
				updateOldTagInfo.setCorrectPlaceNo(newTagInfo.getPlaceNo());
				
				tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
				
				return true;
			}else if("3".equals(unifiedParam.getType())){	
				TagInfo oldTagInfo = tagInfoDao.findByTagNo(tagInfo.getTagNo());
				//保存到历史表
				TagInfoHis tagInfoHis = new TagInfoHis();
				
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis.setId(Long.parseLong(seq.toString()));
				tagInfoHis.setCreateReason("电子标签停用更新");
				tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
				
				saveObuServiceFlowRecord("3", oldTagInfo, tagInfo, null);
				//更新电子标签发行记录的客户ID字段，值为空（NULL）,状态为停用（‘5’）。
				//tagStopDao.updateTagInfo(tagInfo.getId(),tagInfoHis.getId());
				//2017/4/19 修改：需要将原电子标签的修正人信息update
				TagInfo updateOldTagInfo = new TagInfo();
				updateOldTagInfo.setTagState("2");//挂起
				if(BlackFlagEnum.unblack.getValue().equals(tagInfo.getBlackFlag())){
					//2017-08-12
					//有标签stop才要更新回写标志字段
					updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
				}
				updateOldTagInfo.setHisSeqID(tagInfoHis.getId());
				updateOldTagInfo.setBlackFlag(tagInfo.getBlackFlag());
				
				updateOldTagInfo.setId(oldTagInfo.getId());
				updateOldTagInfo.setCorrectTime(tagInfo.getCorrectTime());//更新时间
				updateOldTagInfo.setCorrectOperID(tagInfo.getCorrectOperID());
				updateOldTagInfo.setCorrectOperName(tagInfo.getCorrectOperName());
				updateOldTagInfo.setCorrectOperNo(tagInfo.getCorrectOperNo());
				updateOldTagInfo.setCorrectPlaceID(tagInfo.getCorrectPlaceID());
				updateOldTagInfo.setCorrectPlaceName(tagInfo.getCorrectPlaceName());
				updateOldTagInfo.setCorrectPlaceNo(tagInfo.getCorrectPlaceNo());
				
				tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
				
				
				//更新车卡电子标签绑定表的电子标签为NULL.
				/*tagStopDao.updateCarObuCardInfo(carObuCardInfo.getVehicleID());*/
				carObuCardInfo.setTagID(null);
				carObuCardInfoDao.updateTagNo(carObuCardInfo);
				//修改绑定标志
				tagInfoService.saveSetBind("0", carObuCardInfo.getVehicleID());
				return true;
			}else if("4".equals(unifiedParam.getType())){
				TagInfoHis tagInfoHis = new TagInfoHis();
				
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis.setId(Long.parseLong(seq.toString()));
				tagInfoHis.setCreateReason("电子注销");
				tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
				
				//更新电子标签发行记录的客户ID字段，值为空（NULL）,状态为注销（‘4’）。
//				saveObuServiceFlowRecord("5", oldTagInfo, tagInfo, null);
				//tagInfoCancelDao.updateTagInfo(tagInfo.getId(),tagInfoHis.getId());
				//2017/4/19 修改：需要将原电子标签的修正人信息update
				TagInfo updateOldTagInfo = new TagInfo();
				updateOldTagInfo.setTagState("4");//注销
				if(BlackFlagEnum.unblack.getValue().equals(tagInfo.getBlackFlag())){
					//2017-08-12
					//有标签注销才要更新回写标志字段
					updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
				}
				updateOldTagInfo.setHisSeqID(tagInfoHis.getId());
				updateOldTagInfo.setBlackFlag(tagInfo.getBlackFlag());
				
				updateOldTagInfo.setId(tagInfo.getId());
				updateOldTagInfo.setCorrectTime(tagInfo.getCorrectTime());//更新时间
				updateOldTagInfo.setCorrectOperID(tagInfo.getCorrectOperID());
				updateOldTagInfo.setCorrectOperName(tagInfo.getCorrectOperName());
				updateOldTagInfo.setCorrectOperNo(tagInfo.getCorrectOperName());
				updateOldTagInfo.setCorrectPlaceID(tagInfo.getCorrectPlaceID());
				updateOldTagInfo.setCorrectPlaceName(tagInfo.getCorrectPlaceName());
				updateOldTagInfo.setCorrectPlaceNo(tagInfo.getCorrectPlaceNo());
				
				tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
				
				
				//更新车卡电子标签绑定表的电子标签为NULL.
				/*tagInfoCancelDao.updateCarObuCardInfo(carObuCardInfo.getVehicleID());*/
				if(carObuCardInfo.getVehicleID() != null){
					carObuCardInfo.setTagID(null);
					carObuCardInfoDao.updateTagNo(carObuCardInfo);
					//修改绑定标志
					tagInfoService.saveSetBind("0", carObuCardInfo.getVehicleID());
				}
				//tagInfo.setTagState("4");
				tagInfo.setTagState(TagStateEnum.cancel.getValue());
				return true;
			}else if("5".equals(unifiedParam.getType())){
				//迁移改变了与标签绑定的车辆，没有改变标签状态
				BigDecimal SEQ_CSMSOBUActDetailHis_NO = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				TagInfoHis tagTnfoHis = new TagInfoHis();
				tagTnfoHis.setId(Long.valueOf(SEQ_CSMSOBUActDetailHis_NO.toString()));
				tagTnfoHis.setCreateReason("电子标签迁移重新与车辆进行绑定");
				tagTnfoHis.setCreateDate(new Date());
//				saveObuServiceFlowRecord("4", oldTagInfo, tagInfo, null);
				tagInfoHisDao.saveHis(tagTnfoHis,tagInfo);
				//tagInfoMigrateDao.update(tagInfo,tagInfo.getClientID(),carObuCardInfo.getVehicleID());
				
				//2017/4/19 修改：需要将原电子标签的修正人信息update
				TagInfo updateOldTagInfo = new TagInfo();
				updateOldTagInfo.setTagState("1");//正常
				updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
				updateOldTagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				updateOldTagInfo.setClientID(tagInfo.getClientID());
				updateOldTagInfo.setHisSeqID(tagTnfoHis.getId());
				
				updateOldTagInfo.setId(tagInfo.getId());
				updateOldTagInfo.setCorrectTime(tagInfo.getCorrectTime());//更新时间
				updateOldTagInfo.setCorrectOperID(tagInfo.getCorrectOperID());
				updateOldTagInfo.setCorrectOperName(tagInfo.getCorrectOperName());
				updateOldTagInfo.setCorrectOperNo(tagInfo.getCorrectOperNo());
				updateOldTagInfo.setCorrectPlaceID(tagInfo.getCorrectPlaceID());
				updateOldTagInfo.setCorrectPlaceName(tagInfo.getCorrectPlaceName());
				updateOldTagInfo.setCorrectPlaceNo(tagInfo.getCorrectOperNo());
				
				tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
				
				
				CarObuCardInfo tagOldBind = carObuCardInfoDao.findByTagid(tagInfo.getId());//绑定表以车辆id为标识，有找到电子标签id证明有绑定车辆
				String cardNo = "";
				String cardType = "";
				if(tagOldBind!=null){	//如果原来的标签有绑定车辆，就把绑定记录删掉(标签与卡没有直接关系，不考虑)
					//carObuCardInfoDao.deleteByVehicleId(tagOldBind.getVehicleID());
					CarObuCardInfo c=new CarObuCardInfo();
					c.setVehicleID(tagOldBind.getVehicleID());
					carObuCardInfoDao.updateTagNo(c);
					tagInfoService.saveSetBind("0", tagOldBind.getVehicleID());//修改记帐卡或者储值卡的标签绑定标志为未绑定
					
					//2017/05/23
					VehicleInfo vehicle = vehicleInfoDao.findById(tagOldBind.getVehicleID());
					//写给铭鸿的清算数据：用户状态信息
					cardObuService.saveUserStateInfo(tagTnfoHis.getCreateDate(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签迁移业务：标签与原车辆解除绑定");
					
				}
				CarObuCardInfo newVehicleBind = carObuCardInfoDao.findByVehicleID(carObuCardInfo.getVehicleID());//迁移目标车辆绑定关系
				if(newVehicleBind!=null){//目标车辆有绑定，直接更新目标绑定记录的标签，考虑卡绑定
					newVehicleBind.setTagID(carObuCardInfo.getTagID());
					carObuCardInfoDao.update(newVehicleBind);
					tagInfoService.saveSetBind("1", carObuCardInfo.getVehicleID());//修改记帐卡或者储值卡的标签绑定标志为已绑定
				}else{//目标车辆原先没绑定记录，就新增绑定记录，新增记录是没有卡绑定的
					carObuCardInfoDao.save(carObuCardInfo);					
				}
				
				
				//2017/05/23
				//目标车辆
				VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				//写给铭鸿的清算数据：用户状态信息
				cardObuService.saveUserStateInfo(tagTnfoHis.getCreateDate(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
						cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
						tagInfo.getTagNo(),tagInfo.getObuSerial(), tagInfo.getStartTime(), tagInfo.getEndTime(), "标签迁移业务：标签与目标车辆绑定");
				
				return true;
			}else if("6".equals(unifiedParam.getType())){
				TagInfoHis tagInfoHis = new TagInfoHis();
				
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis.setId(Long.parseLong(seq.toString()));
				tagInfoHis.setCreateReason("电子标签删除");
				
				carObuCardInfo.setTagID(null);
				
				/*saveObuServiceFlowRecord("7", oldTagInfo, tagInfo, unifiedParam.getMainAccountInfo());*/
				tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
				
				carObuCardInfoDao.updateTagNo(carObuCardInfo);
				
				//清算接口    //原清算数据，没用了
				/*UserInfoBaseList userInfoBaseList=new UserInfoBaseList();
				userInfoBaseList.setNetNo("4401");
				//userInfoBaseList.setIssuerId("");发行方唯一标识
				//userInfoBaseList.setAgent();发行代理单位编码
				userInfoBaseListDao.save(userInfoBaseList, null, tagInfo);*/
				
				tagInfoDao.delete(tagInfo.getId());
				/*carObuCardInfoDao.deleteByTagNo(tagInfo.getTagNo());*/
				
				//tagTakeDetailDao.updateTagStateByTagNo("0", tagInfo.getTagNo());
				return true;
			}else if("7".equals(unifiedParam.getType())){
				TagMainRecord tagMainRecord=unifiedParam.getTagMainRecord();
				TagInfo backTagInfo=tagInfo;
				//保存备用标签记录
				if(backTagInfo!=null){
					//backTagInfo.setTagState("1");//正常
					backTagInfo.setTagState(TagStateEnum.normal.getValue());
					//backTagInfo.setIssueType("3");//备件标签发行备件标签发行
					backTagInfo.setIssueType(TagIssueTypeEnum.backTagIss.getValue());
					backTagInfo.setWriteBackFlag("0");
					
					//2017/06/02 set修正人信息
					backTagInfo.setCorrectTime(new Date());//更新时间
					backTagInfo.setCorrectOperID(tagMainRecord.getOperID());
					backTagInfo.setCorrectOperName(tagMainRecord.getOperName());
					backTagInfo.setCorrectOperNo(tagMainRecord.getOperNo());
					backTagInfo.setCorrectPlaceID(tagMainRecord.getIssueplaceID());
					backTagInfo.setCorrectPlaceName(tagMainRecord.getPlaceName());
					backTagInfo.setCorrectPlaceNo(tagMainRecord.getPlaceNo());
					
					tagInfoDao.save(backTagInfo);
					carObuCardInfo.setTagID(backTagInfo.getId());
					
				}else{
					tagInfoService.saveSetBind("0",tagMainRecord.getVehicleID());//车辆obu绑定标志
				}
				//保存源标签历史
				TagInfoHis tagInfoHis = new TagInfoHis();
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis.setId(Long.parseLong(seq.toString()));
				tagInfoHis.setCreateReason("电子标签维修登记");
				if(tagInfo!=null){
					tagInfo.setId(tagMainRecord.getTagInfoID());
					tagInfoHisDao.saveHis(tagInfoHis, tagInfo);					
				}
				//更新原标签状态
				//tagMaintainDao.updateTagInfo(tagMainRecord.getTagInfoID(), tagInfoHis.getId(),3);
				
				//2017/6/02 修改：需要将原电子标签的修正人信息update
				TagInfo updateOldTagInfo = new TagInfo();
				updateOldTagInfo.setTagState("3");//维修
				updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
				//updateOldTagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());//维修不需要下黑名单
				updateOldTagInfo.setHisSeqID(tagInfoHis.getId());
				
				updateOldTagInfo.setId(tagMainRecord.getTagInfoID());
				updateOldTagInfo.setCorrectTime(new Date());//更新时间
				updateOldTagInfo.setCorrectOperID(tagMainRecord.getOperID());
				updateOldTagInfo.setCorrectOperName(tagMainRecord.getOperName());
				updateOldTagInfo.setCorrectOperNo(tagMainRecord.getOperNo());
				updateOldTagInfo.setCorrectPlaceID(tagMainRecord.getIssueplaceID());
				updateOldTagInfo.setCorrectPlaceName(tagMainRecord.getPlaceName());
				updateOldTagInfo.setCorrectPlaceNo(tagMainRecord.getPlaceNo());
				
				tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
				
				
				/*tagMaintainDao.updateCarObuCardInfo(null,tagMainRecord.getVehicleID());*/
				//carObuCardInfo.setTagID(null);
				carObuCardInfo.setVehicleID(tagMainRecord.getVehicleID());
				carObuCardInfoDao.updateTagNo(carObuCardInfo);
				/*saveObuServiceFlowRecord("6", oldTagInfo, tagInfo, null);*/
				
				
				VehicleInfo vehicle = vehicleInfoDao.findById(carObuCardInfo.getVehicleID());
				String cardNo = "";
				String cardType = "";
				
				TagInfo oldTag = tagInfoDao.findById(tagMainRecord.getTagInfoID());
				if(oldTag!=null){
					//写给铭鸿的清算数据：用户状态信息
					cardObuService.saveUserStateInfo(new Date(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							oldTag.getTagNo(),oldTag.getObuSerial(), oldTag.getStartTime(), oldTag.getEndTime(), "标签维修：维修登记原标签与车辆解除绑定");
				}
				if(backTagInfo != null){
					//2017/05/23
					//写给铭鸿的清算数据：用户状态信息
					cardObuService.saveUserStateInfo(new Date(), Integer.parseInt(UserStateInfoDealFlagEnum.bindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							backTagInfo.getTagNo(),backTagInfo.getObuSerial(), backTagInfo.getStartTime(), backTagInfo.getEndTime(), "标签维修：维修登记并发行备件标签");
				}
				
				
				
				return true;
			}else if("8".equals(unifiedParam.getType())){
				saveObuServiceFlowRecord("1", null, tagInfo, unifiedParam.getMainAccountInfo());
				tagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				tagInfoDao.save(tagInfo);
				/*carObuCardInfoDao.save(carObuCardInfo);*/
				carObuCardInfoDao.updateTagNo(carObuCardInfo);
				tagTakeDetailDao.updateTagStateByTagNo("1", tagInfo.getTagNo());
				return true;
			}else if("9".equals(unifiedParam.getType())){
				TagMainRecord temp=unifiedParam.getTagMainRecord();
				TagInfoHis tagInfoHis2 = new TagInfoHis();
				TagInfo backTagInfo = null;
				if(StringUtil.isNotBlank(temp.getBackupTagNo())){
					//将备件标签的发行记录移除至历史表，生成原因为“维修返回客户，备件标签删除”
					TagInfoHis tagInfoHis = new TagInfoHis();
					BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
					tagInfoHis.setId(Long.parseLong(seq.toString()));
					tagInfoHis.setCreateReason("维修返回客户备件标签删除");
					tagMaintainDao.savetagInfoHis(tagInfoHis,temp.getBackupTagNo());
					//2017/05/23
					backTagInfo = tagInfoDao.findByTagNo(temp.getBackupTagNo());
					//删除备件标签
					tagInfoDao.deleteByTagNo(temp.getBackupTagNo());
				}
				//对备件标签做入库处理，入库类型为“回收入库？”
				//答：2016-07-26 确定为备件冲正。在电子标签维修返回客户业务时首先调用营运提供的备件冲正接口
				
				//将原电子标签记录移至历史表，生成原因为“电子标签维修返回客户，重新绑定电子标签与车辆“
				tagInfo =tagInfoDao.findById(temp.getTagInfoID());
				BigDecimal seq2 = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis2.setId(Long.parseLong(seq2.toString()));
				tagInfoHis2.setCreateReason("电子标签维修返回客户");
				tagInfoHisDao.saveHis(tagInfoHis2, tagInfo);
				
				saveObuServiceFlowRecord("8", tagInfo, tagInfo, unifiedParam.getMainAccountInfo());
				//修改状态为正常（1）
				//tagMaintainDao.updateTagInfo(temp.getTagInfoID(), tagInfoHis2.getId(),1);
				
				//2017/6/02 修改：需要将原电子标签的修正人信息update
				TagInfo updateOldTagInfo = new TagInfo();
				updateOldTagInfo.setTagState("2");//停用   guanshaofeng 既然维修返回客户，没有绑定车辆而要去迁移界面做迁移，则此时应该是停用状态
				updateOldTagInfo.setWriteBackFlag("0");//这个时候改为未回写，要操作员去做回写功能把这个字段update为已回写
				updateOldTagInfo.setHisSeqID(tagInfoHis2.getId());
				
				updateOldTagInfo.setId(temp.getTagInfoID());
				updateOldTagInfo.setCorrectTime(new Date());//更新时间
				updateOldTagInfo.setCorrectOperID(temp.getBackOperID());
				updateOldTagInfo.setCorrectOperName(temp.getOperName());
				updateOldTagInfo.setCorrectOperNo(temp.getOperNo());
				updateOldTagInfo.setCorrectPlaceID(temp.getIssueplaceID());
				updateOldTagInfo.setCorrectPlaceName(temp.getPlaceName());
				updateOldTagInfo.setCorrectPlaceNo(temp.getPlaceNo());
				
				tagInfoDao.updateNotNullTagInfo(updateOldTagInfo);
				
				//重新绑定,改车卡绑定表的标签id为原来的电子标签id where 车辆id为原来那辆车
				/*tagMaintainDao.updateCarObuCardInfo(temp.getTagInfoID(), temp.getVehicleID());*/
				//carObuCardInfo.setTagID(temp.getTagInfoID());
				//维修返回客户,不绑定车辆，页面提示去迁移页面绑定
				
				//2017-09-01 如果维修没有备件标签，维修过程中车辆绑定了另外标签，那么维修返回客户就不应该将该车辆绑定的另外标签解绑.
				if(backTagInfo != null){
					carObuCardInfo = carObuCardInfoDao.findByVehicleID(temp.getVehicleID());
					if(carObuCardInfo != null){
						//如果车辆绑定的是该备件标签,就要解绑
						if(backTagInfo.getId().equals(carObuCardInfo.getTagID())){
							carObuCardInfo.setVehicleID(temp.getVehicleID());
							carObuCardInfo.setTagID(null);
							carObuCardInfoDao.updateTagNo(carObuCardInfo);
							tagInfoService.saveSetBind("0",temp.getVehicleID());//修改为未绑定
						}
					}else{
						logger.warn("数据异常：无法找到车卡标签绑定信息");
					}
				}
				
				if(backTagInfo != null){
					String cardNo = "";
					String cardType = "";
					VehicleInfo vehicle = vehicleInfoDao.findById(temp.getVehicleID());
					//写给铭鸿的清算数据：用户状态信息
					cardObuService.saveUserStateInfo(new Date(), Integer.parseInt(UserStateInfoDealFlagEnum.unbindCarAndObu.getValue()), cardNo, 
							cardType, Integer.parseInt(vehicle.getVehicleColor()), vehicle.getVehiclePlate(), vehicle.getNSCVehicleType(), 
							backTagInfo.getTagNo(),backTagInfo.getObuSerial(), backTagInfo.getStartTime(), backTagInfo.getEndTime(), "标签维修：维修返回客户并解除备件标签与车辆的绑定");
				}
				
				
				return true;
			}else if("10".equals(unifiedParam.getType())){
				TagInfoHis tagInfoHis = new TagInfoHis();
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
				tagInfoHis.setId(Long.parseLong(seq.toString()));
				tagInfoHis.setCreateReason("电子标签回写");
				tagInfo.setHisSeqID(tagInfoHis.getId());
				tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
				tagInfo.setBlackFlag(BlackFlagEnum.unblack.getValue());
				tagInfo.setWriteBackFlag("1");
				tagInfoDao.update(tagInfo);
				return true;
			}
			throw new ApplicationException("OBU接口调用失败");
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"OBU接口调用失败");
			e.printStackTrace();
			throw new ApplicationException("OBU接口调用失败");
		}
	}
	
	/**
	 * 客服流水保存
	 * 31为发行 32为更换 33为停用 34为迁移 35为注销 36为维修 37删除 38维修返还客户
	 * @param type
	 * @param oldTagInfo 修改前对象
	 * @param newTagInfo 修改后对象
	 * @param mainAccountInfo  主账户对象 不存在更改账户的置为null
	 */
	public void saveObuServiceFlowRecord(String type,TagInfo oldTagInfo,TagInfo newTagInfo,MainAccountInfo mainAccountInfo){
		ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
		serviceFlowRecord.setId(Long.parseLong(seq.toString()));
		serviceFlowRecord.setServiceFlowNO(seq.toString());
		
		if("1".equals(type) && !"0".equals(newTagInfo.getChargeCost().toString())){
			/*serviceFlowRecord.setClientID(newTagInfo.getClientID());
			
			getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setCurrAvailableBalance(newTagInfo.getChargeCost());
			serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
			serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
			serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
			serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));
			
			serviceFlowRecord.setAfterAvailableBalance(new BigDecimal("-"+newTagInfo.getChargeCost()));
			serviceFlowRecord.setAfterFrozenBalance(new BigDecimal("0"));
			serviceFlowRecord.setAfterpreferentialBalance(new BigDecimal("0"));
			serviceFlowRecord.setAfterRefundApproveBalance(new BigDecimal("0"));
			serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("0"));
			
			serviceFlowRecord.setBeforeState(null);
			serviceFlowRecord.setCurrState(newTagInfo.getTagState());
			serviceFlowRecord.setAfterState(newTagInfo.getTagState());
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(Integer.parseInt(type));
			
			serviceFlowRecord.setIsDoFlag("0");
			serviceFlowRecordDao.saveBalanceServiceFlow(serviceFlowRecord);*/
			return;
			
		}else if("1".equals(type) && "0".equals(newTagInfo.getChargeCost().toString())){
			serviceFlowRecord.setClientID(newTagInfo.getClientID());
			serviceFlowRecord.setBeforeState(null);
			serviceFlowRecord.setCurrState(newTagInfo.getTagState());
			serviceFlowRecord.setAfterState(newTagInfo.getTagState());
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(Integer.parseInt(type));
		}else if("7".equals(type) && "1".equals(oldTagInfo.getIssueType())){//删除   提货发行一次完成
			/*serviceFlowRecord.setClientID(oldTagInfo.getClientID());
			getBeforeServiceFlowRecord(mainAccountInfo,serviceFlowRecord);
			serviceFlowRecord.setCurrAvailableBalance(oldTagInfo.getChargeCost());
			serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
			serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
			serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
			serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));
			
			serviceFlowRecord.setAfterAvailableBalance(oldTagInfo.getChargeCost());
			serviceFlowRecord.setAfterFrozenBalance(new BigDecimal("0"));
			serviceFlowRecord.setAfterpreferentialBalance(new BigDecimal("0"));
			serviceFlowRecord.setAfterRefundApproveBalance(new BigDecimal("0"));
			serviceFlowRecord.setAfterAvailableRefundBalance(new BigDecimal("0"));
			
			serviceFlowRecord.setBeforeState(oldTagInfo.getTagState());
			serviceFlowRecord.setCurrState(oldTagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(7);//发行删除
			serviceFlowRecord.setIsDoFlag("0");
			serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord);*/
			return;
		}else if("7".equals(type) && "2".equals(oldTagInfo.getIssueType())){// 删除 先提货后发行
			serviceFlowRecord.setClientID(oldTagInfo.getClientID());
			serviceFlowRecord.setBeforeState(oldTagInfo.getTagState());
			serviceFlowRecord.setCurrState(oldTagInfo.getTagState());
			serviceFlowRecord.setAfterState(null);
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(7);//发行删除
		}else{
			if(oldTagInfo!=null){
				serviceFlowRecord.setBeforeState(oldTagInfo.getTagState());
				serviceFlowRecord.setOldCardTagNO(oldTagInfo.getTagNo());
			}
			serviceFlowRecord.setClientID(oldTagInfo.getClientID());
			if(newTagInfo!=null){
				serviceFlowRecord.setCurrState(newTagInfo.getTagState());
				serviceFlowRecord.setAfterState(newTagInfo.getTagState());	
				serviceFlowRecord.setCardTagNO(newTagInfo.getTagNo());
			}
			serviceFlowRecord.setServicePTypeCode(3);
			serviceFlowRecord.setServiceTypeCode(Integer.parseInt(type));
		}
		serviceFlowRecord.setCreateTime(new Date());
		serviceFlowRecord.setIsDoFlag("0");
		serviceFlowRecordDao.saveTagInfoServiceFlow(serviceFlowRecord,"1");
		
	}
	
	/**
	 * 客户流水更新前
	 * @param newMainAccountInfo
	 * @param serviceFlowRecord
	 * @return
	 */
	private ServiceFlowRecord getBeforeServiceFlowRecord(MainAccountInfo newMainAccountInfo,ServiceFlowRecord serviceFlowRecord){
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_ServiceFlow_Record_NO");
		serviceFlowRecord.setId(Long.parseLong(seq.toString()));
		serviceFlowRecord.setClientID(newMainAccountInfo.getMainId());
		serviceFlowRecord.setServiceFlowNO(seq.toString());
		
		serviceFlowRecord.setBeforeAvailableBalance(newMainAccountInfo.getAvailableBalance());
		serviceFlowRecord.setBeforeFrozenBalance(newMainAccountInfo.getFrozenBalance());
		serviceFlowRecord.setBeforepreferentialBalance(newMainAccountInfo.getPreferentialBalance());
		serviceFlowRecord.setBeforeAvailableRefundBalance(newMainAccountInfo.getAvailableRefundBalance());
		serviceFlowRecord.setBeforeRefundApproveBalance(newMainAccountInfo.getRefundApproveBalance());
		serviceFlowRecord.setCurrFrozenBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrpreferentialBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrAvailableRefundBalance(new BigDecimal("0"));
		serviceFlowRecord.setCurrRefundApproveBalance(new BigDecimal("0"));
		
		return serviceFlowRecord;
	}
}
