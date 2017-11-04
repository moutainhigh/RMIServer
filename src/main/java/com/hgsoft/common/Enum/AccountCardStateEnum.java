package com.hgsoft.common.Enum;

public enum AccountCardStateEnum {
	
	nomal("正常","0"),loss("挂失","1"),cancel("注销","2"),
	disabled("挂起","3");
	
	private String name;

    private String index;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private AccountCardStateEnum(String name,String index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(String index) {
        for (AccountCardStateEnum c : AccountCardStateEnum .values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

	
	
}
