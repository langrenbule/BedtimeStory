package com.deity.bedtimestory.utils;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;

import com.deity.bedtimestory.BuildConfig;

import java.util.Iterator;
import java.util.LinkedHashSet;

import cn.yunzhisheng.tts.offline.TTSPlayerListener;
import cn.yunzhisheng.tts.offline.basic.ITTSControl;
import cn.yunzhisheng.tts.offline.basic.TTSFactory;

/**
 * 离线语音解析
 */
public class SpeechUtilOffline implements TTSPlayerListener {
	public static final String appKey = "_appKey_";
	public static final String  secret = "_secret_";
	private ITTSControl mTTSPlayer;
	private Context context;

	//待播放的语音集合 Add 2016-04-14 by fengwenhua
	private LinkedHashSet<String> voiceStrSet = new LinkedHashSet<>();
	//语音引擎是否处于空闲状态
	private boolean isVoiceEngineIdle=true;

	public SpeechUtilOffline(Context context) {
		this.context = context;
	}

	/**
	 * 初始化引擎
	 */
	public void init() {
		mTTSPlayer = TTSFactory.createTTSControl(context, appKey);// 初始化语音合成对象
		mTTSPlayer.setTTSListener(this);// 设置回调监听
		mTTSPlayer.setStreamType(AudioManager.STREAM_MUSIC);//设置音频流,默认为:AudioManager.STREAM_MUSIC
		mTTSPlayer.setVoiceSpeed(1.0f);//设置播报语速,播报语速，数值范围 0.1~2.5 默认为 1.0
		mTTSPlayer.setVoicePitch(1.0f);//设置播报音高,调节音高，数值范围 0.9～1.1 默认为 1.0
		mTTSPlayer.init();// 初始化合成引擎
		mTTSPlayer.setDebug(BuildConfig.DEBUG);//设置是否是debug模式 add zhangyl
		mTTSPlayer.setPlayStartBufferTime(100);//单位:毫秒
	}

	/**
	 * 停止播放
	 */
	public void stop(){
		mTTSPlayer.stop();
	}

	/**
	 * 播放
	 * @param content 要播放的内容(字符串)
	 */
	public void play(String content) {
		if(!TextUtils.isEmpty(content)){
			try {
				mTTSPlayer.play(content.toUpperCase());//MyVoiceUtils.numbers2chinese(
			}catch (Exception e){
//				KTLog.e("语音播报错误",e);//主要是catch java.lang.IllegalThreadStateException: Thread already started
			}
		}
	}

	public void addVoiceData(String voiceStr){
		if (!TextUtils.isEmpty(voiceStr)){//声音数据为空,无需添加
			voiceStrSet.add(voiceStr);
		}
		playNextVoice();
	}

	/**检测待播放的数据是否为空*/
	private  boolean isVoiceListEmpty(){
		return (null==voiceStrSet||voiceStrSet.isEmpty());
	}

	/**该方法需要同步*/
	private synchronized void playNextVoice(){
//		KTLog.i("队列中是否为空:"+isVoiceListEmpty()+"当前引擎是否空闲:"+isVoiceEngineIdle);
		if (!isVoiceListEmpty()&&isVoiceEngineIdle){
			isVoiceEngineIdle=false;
			play(getFirst());
		}
	}

	/**获取第一条数据*/
	public String getFirst(){
		Iterator<String> iterator = voiceStrSet.iterator();
		if(iterator.hasNext()){
			String voiceStr = iterator.next();
			voiceStrSet.remove(voiceStr);
			return voiceStr;
		}
		return null;
	}

	private void initVoiceList(){
		isVoiceEngineIdle=true;
		voiceStrSet.clear();
	}

	/**
	 * 取消播放
	 */
	public void cancel(){
		mTTSPlayer.cancel();
	}
	/**
	 * 释放资源
	 */
	public void release() {
		// 主动释放离线引擎
		mTTSPlayer.release();
	}

	@Override
	public void onPlayEnd() {
		// 播放完成回调
		Log.i("msg", "onPlayEnd");
		isVoiceEngineIdle=true;
		playNextVoice();
	}

	@Override
	public void onPlayBegin() {
		// 开始播放回调
		Log.i("msg", "onPlayBegin");
	}

	@Override
	public void onInitFinish() {
		// 初始化成功回调
		Log.i("msg", "onInitFinish");
		initVoiceList();
	}

	@Override
	public void onError(cn.yunzhisheng.tts.offline.common.USCError arg0) {
		// 语音合成错误回调
		Log.i("msg", "onError");
		initVoiceList();
	}

	@Override
	public void onCancel() {
		// 取消播放回调
		Log.i("msg", "onCancel");
		initVoiceList();
	}

	@Override
	public void onBuffer() {
		// 开始缓冲回调
		Log.i("msg", "onBuffer");

	}
}
