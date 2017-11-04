package com.hgsoft.acms.other.serviceInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface IEmboitementTransferServiceACMS {
	public VehicleInfo findById(Long id);
	public Pager findByPlateAndColor(Pager pager, Customer customer, String plate, String color, String cardno, String tagno);
	public boolean saveTransfer(VehicleInfo vehicleInfo,Long customerId,Long newCutomerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);
}
