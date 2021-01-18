package src.domain;

public enum Level {
    Beginner ("Beginner"),
    Advanced ("Advanced"),
    Expert ("Expert");


    private Object code;
	
	private Level(Object code){
		this.code=code;
	}

	public static Level getByCode(Object levelCode) {
		for(Level s: Level.values()){
			if(s.code.equals(levelCode)){
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
