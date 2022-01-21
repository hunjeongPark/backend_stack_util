public class ConfigEnum {
  
  //Config Url this create the use Config Class whatever things 'configDB.JDBC.make()' ...
  
  public enum configDB {
		JDBC {
			@Override
			public String make() {
				if (IS_REAL_MODE) {
					return CommonConfig.ConfigJdbcUrl_REAL;
				} else {
					return CommonConfig.ConfigJdbcUrl_TEST;
				}
			}
		},
		
		USER {
			@Override
			public String make() {
				if (IS_REAL_MODE) {
					return CommonConfig.ConfigJdbcUserName_REAL;
				} else {
					return CommonConfig.ConfigJdbcUserName_TEST;
				}
			}
		},
		
		PSWD {
			@Override
			public String make() {
				if (IS_REAL_MODE) {
					return CommonConfig.ConfigJdbcPassword_REAL;
				} else {
					return CommonConfig.ConfigJdbcPassword_TEST;
				}
			}
		};
		
		public abstract String make();
	}
	
	public enum configUrl {
		IMG {
			@Override
			public String url() {
				if (IS_REAL_MODE) {
					return CommonConfig.HttpImgUrl_REAL;
				} else {
					return CommonConfig.HttpImgUrl_TEST;
				}
			}
		},
		
		FILESAVE {
			@Override
			public String url() {
				if (IS_REAL_MODE) {
					return CommonConfig.FileSavePath_REAL + DateUtil.getToday("yyyyMMdd");
				} else {
					return CommonConfig.FileSavePath_TEST + DateUtil.getToday("yyyyMMdd");
				}
			}
		};
		
		public abstract String url();
	}

}
