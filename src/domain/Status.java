package src.domain;

public enum Status {
    Concept ("Concept"),
    Active ("Active"),
	Archived ("Archived");
	
	

	private Object code;
	
	private Status(Object code){
		this.code=code;
	}

	public static Status getByCode(Object statusCode) {
		for(Status s: Status.values()){
			if(s.code.equals(statusCode)){
				return s;
			}
		}
		return null;
	}

	public Object getCode() {
		return code;
	}

	@Override
    public String toString() {
        return this.getCode().toString();
    }



	


}
