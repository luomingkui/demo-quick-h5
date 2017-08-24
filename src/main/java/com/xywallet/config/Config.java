package com.xywallet.config;

public class Config {

	// 支付配置
	//服务地址
	public static String SERVICE_URL = "https://testapi.walletxx.com/xywallet-api/api";
	//成功后,异步回调地址(填写为测试使用的回调地址,demo需要将http://godxsj.xywallet.com/部分替换掉就可以)
	public static String DEFAULT_NOTIFY_URL = "http://godxsj.xywallet.com/demo-quick-page/notify/quickpay";
	//AES秘钥
	public static String QUICK_PAY_AES_KEY = "CRu3Yc58KCPRo4PQpJa1yw==";
	//RSA秘钥
	public static String MERCHANT_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANMsS9dadOOnGuZ7E+LsCJN38Pg6QQh6NnRYSyEg3bh/zu4kAkRKMJUDKTV0cQtwZw9Tpoovc1jnvWmllLcmdjy3WHJcbH+OR+OIWN1sSAer9yiHMxoNFYfrJBc8bS2OxLcHDN3DXrGzDVv3C0tl+KUJLy9jtcl79N6A6HvKyQkXAgMBAAECgYEAlzLEKrFtuAJR1GyTVIrDqTLbqh+rqLI4gx0kzdeGaS+5rfDCXrrwBgF5Y/i3aAVXBTZTq+VFpYZnrFkOHgS/6nQxphhUFzhBATsRaKRw02ZgKEDpSktQM/n+jrSPc7AN/wA4S8a1OLUxtXJcCPfFkbImPwByZMD5mIWUyR4e1EECQQDrW06JksUPv+ydqUgGLki63i9wYyZ0kUxI17ui66Gl1Y5wCiZpPOwyNYT9dvD1xUZj8aBWGkw9tglc5nYNIcypAkEA5bH4fsGjjpHGzDUNPO9u0FgoHF5eoccJGlSD/3iN5gA1BEILkhRU/rlccOs16I9SYfvYS/Jbrv1VxF/dyi3/vwJAY3RsFbqNx4AEzwLvypzYYw7s4QMlBvE3WTjAs7H61wcNb876OFJkRTtUfX87h7/bZPSAGqJ2QUWhYz6H+DimMQJAOad8WkS8ixKoNaWzVvI6fG1EVCqT0kOlU0iXgU5/P5YZuKHjSZ104CMiwu0nMOKYqSOP3TBONxQ7rPjrMM5u/QI/YHbUmgvTpKWNyiPIDr0dCLbScV2T9M5GMogNA2I3SMpxyjObSLCvpTwEM9pO+gSKnltYQHPxhb2smCXoMS9j";
	//RSA验签公钥
	public static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJvXIrpaJ7GnVcGW1HxLV+nLr6n5ncPk2JgMho8gilFISY4Xa7GKcOWLoJDUUaySHel09O2/gmq92FkpK6AdPct+nMkMRWRqCCU/rXgT9Mmhe8V8X29YoKsDlF+xLHNxxJ58eZDdqNSxmHZ03NIdeHf94TFZQ+O8gvOt7NpX1R9wIDAQAB";
	//商户APPID
	public static String APP_ID = "88888";


}
