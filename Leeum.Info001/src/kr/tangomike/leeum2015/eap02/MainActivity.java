package kr.tangomike.leeum2015.eap02;

/**
 * 2013. shimaz.BSH @tangomike
 * bshlab 
 * 
 * 다섯손가락 터치되면 설정창.
 * 헬프는 없음 
 * 
 * 타이머 / 핸들러 구현 
 *  
 */	



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

// osc components 
import tuioDroid.osc.OSCInterface;
import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;

// network resources
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;


@SuppressLint({ "DefaultLocale", "HandlerLeak" })
public class MainActivity extends Activity {
	
	
	private boolean isConfigurable = true; // enable - disable access to settings Activity 
	
	// osc values & set default values
	private String oscIP;
	private int oscPort;
	private OSCInterface oscInterface;
	
	private static final int REQUEST_CODE_SETTINGS = 0;
	
	private static final int LANG_KOR = 0;
	private static final int LANG_ENG = 1;
	
	private boolean drawAdditionalInfo;
	private boolean sendPeriodicUpdates;
	private int screenOrientation;
//	private SensorManager sensorManager;
	
	private int screenNumber;
	 
//	private boolean showSettings = false;
	
	private int language;

	
	
	/**
	 * Timer Values 
	 */
	
	private Handler mHandler;
	private long tCounter = 0;
	private static final long screenSaverOnTime = 90;
	private boolean isTouched = false;
	
	// TODO 버튼 어레이 생성 
	//ImageView for Buttons

	private boolean running;

	private Button btn01, btn02, btn03, btn04, btnKor, btnEng;
	
	private ImageView imgBg, imgOverlay;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		running = true;
		sendPeriodicUpdates = true;
		
		SharedPreferences setting = this.getPreferences(MODE_PRIVATE);	
		oscIP = "192.168.0.9"; //setting.getString("myIP", "192.168.0.9");
		oscPort = 3333; //setting.getInt("myPort", 3333);
		drawAdditionalInfo = setting.getBoolean("extraInfo", true);
		
		oscInterface = new OSCInterface(oscIP, oscPort);
		
		screenNumber = 0;
		language = LANG_KOR;
		
		
		isTouched = false;
		
		// 새 액티비티를 열지 않고 이곳에서 ImageView의 애니메이션을 사용. 
		// 각 버튼을 터치하거나 할 때 OSCInterface를 통해 통신 

		
		
		RelativeLayout rlMain = new RelativeLayout(this);
		
		
		rlMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rlMain.setBackgroundColor(Color.BLACK);
		
		setContentView(rlMain);
		
		LayoutParams lParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		
		imgBg = new ImageView(this);
		imgBg.setLayoutParams(lParam);
		imgBg.setImageResource(R.drawable.eap_2_image);
		imgBg.setX(0);
		imgBg.setY(0);
		rlMain.addView(imgBg);
		
		
		
		imgOverlay = new ImageView(this);
		imgOverlay.setLayoutParams(lParam);
		imgOverlay.setImageResource(R.drawable.info_01_btn_fake);
		imgOverlay.setX(0);
		imgOverlay.setY(0);
		rlMain.addView(imgOverlay);
		
		
		
		
		btn01 = new Button(this);
		btn02 = new Button(this);
		btn03 = new Button(this);
		btn04 = new Button(this);
		btnKor = new Button(this);
		btnEng = new Button(this);
		
		btn01.setLayoutParams(lParam);
		btn01.setBackgroundResource(R.drawable.eap_2_btn_1_up);
		btn01.setX(59);
		btn01.setY(1008);
		
		
		btn01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}
				
				
				if(screenNumber != 1){
					screenNumber = 1;
					setButtonStatus();
				}
				
				
			}
		});
		rlMain.addView(btn01);
		

		btn02.setLayoutParams(lParam);
		btn02.setBackgroundResource(R.drawable.eap_2_btn_2_up);
		btn02.setX(238);
		btn02.setY(1008);
		btn02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}
				
				
				if(screenNumber != 2){
					screenNumber = 2;
					setButtonStatus();
				}
			}
		});
		
		rlMain.addView(btn02);
		
		
		
		btn03.setLayoutParams(lParam);
		btn03.setBackgroundResource(R.drawable.eap_2_btn_3_up);
		btn03.setX(417);
		btn03.setY(1008);
		btn03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}

				
				if(screenNumber != 3){
					screenNumber = 3;
					setButtonStatus();
					

				}
			}
		});
		
		rlMain.addView(btn03);
		
		
		btn04.setLayoutParams(lParam);
		btn04.setBackgroundResource(R.drawable.eap_2_btn_4_up);
		btn04.setX(596);
		btn04.setY(1008);
		btn04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}

				
				if(screenNumber != 4){
					screenNumber = 4;
					setButtonStatus();
					
				}
				
			}
		});
		

		rlMain.addView(btn04);
		
		
		btnKor.setLayoutParams(lParam);
		btnKor.setBackgroundResource(R.drawable.eap_2_kor_down);
		btnKor.setX(633);
		btnKor.setY(39);
		btnKor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}
				
				if(language != LANG_KOR){
					language = LANG_KOR;
					setButtonStatus();
					
				}

				
			}
		});

		rlMain.addView(btnKor);
		
		btnEng.setLayoutParams(lParam);
		btnEng.setBackgroundResource(R.drawable.eap_2_eng_up);
		btnEng.setX(633);
		btnEng.setY(97);
		btnEng.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}
				
				if(language != LANG_ENG){
					language = LANG_ENG;
					setButtonStatus();
				}

				
			}
		});
	
		rlMain.addView(btnEng);

		
		mHandler = new Handler(){
			public void handleMessage(Message msg){
			
				running = true;
				tCounter++;
				
				if(tCounter <= screenSaverOnTime){
					mHandler.sendEmptyMessageDelayed(0, 1000);
					android.util.Log.i("timer: ", ""+tCounter);
				}else if(tCounter > screenSaverOnTime){
					tCounter = 0;
					screenNumber = 0;
					language = LANG_KOR;
					isTouched = false;
					
					
					setButtonStatus();

				}
			
				
			}
			
			
		};
		
		
		
		

		
//		setButtonStatus();
		
		startOSC();
		
		
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	
	private void setButtonStatus(){
		// TODO 버튼 상태 처리 
		
		switch(screenNumber){
		default:
		case 0: // screensaver;
			btn01.setBackgroundResource(R.drawable.eap_2_btn_1_up);
			btn02.setBackgroundResource(R.drawable.eap_2_btn_2_up);
			btn03.setBackgroundResource(R.drawable.eap_2_btn_3_up);
			btn04.setBackgroundResource(R.drawable.eap_2_btn_4_up);
			imgOverlay.setImageResource(R.drawable.info_01_btn_fake);
			break;
			
			
		case 1:
			btn01.setBackgroundResource(R.drawable.eap_2_btn_1_down);
			btn02.setBackgroundResource(R.drawable.eap_2_btn_2_up);
			btn03.setBackgroundResource(R.drawable.eap_2_btn_3_up);
			btn04.setBackgroundResource(R.drawable.eap_2_btn_4_up);
			imgOverlay.setImageResource(R.drawable.eap_2_overlay_1);
			break;
			
		case 2:
			btn01.setBackgroundResource(R.drawable.eap_2_btn_1_up);
			btn02.setBackgroundResource(R.drawable.eap_2_btn_2_down);
			btn03.setBackgroundResource(R.drawable.eap_2_btn_3_up);
			btn04.setBackgroundResource(R.drawable.eap_2_btn_4_up);
			imgOverlay.setImageResource(R.drawable.eap_2_overlay_2);
			break;
			
		case 3:
			btn01.setBackgroundResource(R.drawable.eap_2_btn_1_up);
			btn02.setBackgroundResource(R.drawable.eap_2_btn_2_up);
			btn03.setBackgroundResource(R.drawable.eap_2_btn_3_down);
			btn04.setBackgroundResource(R.drawable.eap_2_btn_4_up);
			imgOverlay.setImageResource(R.drawable.eap_2_overlay_3);
			break;
			
		case 4:
			btn01.setBackgroundResource(R.drawable.eap_2_btn_1_up);
			btn02.setBackgroundResource(R.drawable.eap_2_btn_2_up);
			btn03.setBackgroundResource(R.drawable.eap_2_btn_3_up);
			btn04.setBackgroundResource(R.drawable.eap_2_btn_4_down);
			imgOverlay.setImageResource(R.drawable.eap_2_overlay_4);
			break;

		}
		
		
		

		// TODO 언어버튼 이미지 토글 처리 
		
		if(language == LANG_KOR){
			btnKor.setBackgroundResource(R.drawable.eap_2_kor_down);
			btnEng.setBackgroundResource(R.drawable.eap_2_eng_up);
		}else{
			btnKor.setBackgroundResource(R.drawable.eap_2_kor_up);
			btnEng.setBackgroundResource(R.drawable.eap_2_eng_down);
		}

		
	}
	
	
	
	
	
	
	@Override 
	public boolean onTouchEvent(MotionEvent event){
		
		
		if(isConfigurable && event.getPointerCount() == 4 && event.getAction() == MotionEvent.ACTION_POINTER_UP)
		{
			
			openSettingsActivity();
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * Opens the Activity that provides the Settings
	 */
    private void openSettingsActivity (){
    	Intent myIntent = new Intent();
    	myIntent.setClassName("kr.tangomike.leeum2015.eap02", "kr.tangomike.leeum2015.eap02.SettingsActivity"); 
    	myIntent.putExtra("IP_in", oscIP);
    	myIntent.putExtra("Port_in", oscPort);
    	myIntent.putExtra("ExtraInfo", this.drawAdditionalInfo);
       	myIntent.putExtra("VerboseTUIO", this.sendPeriodicUpdates);
      	myIntent.putExtra("ScreenOrientation", this.screenOrientation);
//      	showSettings = true;
    	startActivityForResult(myIntent, REQUEST_CODE_SETTINGS);
    }
    
    /**
     * Listens for results of new child activities. 
     * Different child activities are identified by their requestCode
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
       
    	 // See which child activity is calling us back.
    	if(requestCode == REQUEST_CODE_SETTINGS){
        	
        	switch (resultCode){
        	
        		case RESULT_OK:
        			Bundle dataBundle = data.getExtras(); 
        		            			
        	    	String ip = dataBundle.getString("IP");
        	    	
        	    	try { InetAddress.getByName(ip); } 
        	    	catch (Exception e) {
        	    		Toast.makeText(this, "Invalid host name or IP address!", Toast.LENGTH_LONG).show();
        			}
        	    	
        	    	int port = 3333;
        	    	try { port = Integer.parseInt(dataBundle.getString("Port")); }
        	    	catch (Exception e) { port = 0; }
        	    	if (port<1024) Toast.makeText(this, "Invalid UDP port number!", Toast.LENGTH_LONG).show();
        	    		
        	    	this.oscIP = ip;
            	    this.oscPort = port;        	
            	    this.drawAdditionalInfo = dataBundle.getBoolean("ExtraInfo");
            	    this.sendPeriodicUpdates = dataBundle.getBoolean("VerboseTUIO");
            	    	
            	    
            	    	
            	    /* Change behavior of screen rotation */
            	    this.screenOrientation  = dataBundle.getInt("ScreenOrientation");
            	    this.adjustScreenOrientation(this.screenOrientation);
            	    	
        	    	/* Get preferences, edit and commit */
            	    SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
            	    SharedPreferences.Editor editor = settings.edit();
            	    
            	    /* define Key/Value */
            	    editor.putString("myIP", this.oscIP);
            	    editor.putInt("myPort", this.oscPort);
            	    editor.putBoolean("ExtraInfo",this.drawAdditionalInfo);
            	    editor.putBoolean("VerboseTUIO",this.sendPeriodicUpdates);
            	    editor.putInt("ScreenOrientation",this.screenOrientation);
            	    
            	    /* save Settings*/
            	    editor.commit();            	    	        			
         	    	
        	    	break;
        	    
        	    
        	    default:
        	    	// Do nothing
        		
        	}
    	}
    }
    


    /**
     * Adjusts the screen orientation
     */
    private void adjustScreenOrientation (int screenOrientation){
    	
    	switch(screenOrientation){
    	
    		case 0: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
    			
    		case 1: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
				
    		case 2: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
	
    		default: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
    	}	
    }
    
	

	public void sendOSCData () throws ArrayIndexOutOfBoundsException{
		
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build()); 


		
		OSCBundle oscBundle = new OSCBundle();
		Object outputData[] = new Object[2];
		outputData[0] = (Integer) screenNumber;
		outputData[1] = (Integer) language;
		
		oscBundle.addPacket(new OSCMessage("/Void/Leeum", outputData));
	
		oscInterface.sendOSCBundle(oscBundle);
		
		
		android.util.Log.i("osc ", outputData.toString());
	}
	
	public void setNewOSCConnection (String oscIP, int oscPort){	
		oscInterface.closeInteface();
		oscInterface = new OSCInterface(oscIP,oscPort);
	}

    public String getLocalIpAddress() {
       try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {}
        return "127.0.0.1";
        
        
    	
    }
    
    
    public void startOSC(){
    	running = true;

		new Thread(new Runnable() {
		    public void run() {
		      boolean network = oscInterface.isReachable();
		      while (running) {
		    	  
    			  oscInterface.checkStatus();
    			  boolean status = oscInterface.isReachable();
		    	  if (network!=status) {
		    		  network = status;
		    		  	    		  
		    	  }
		    	 
		    	  if (sendPeriodicUpdates) {
		    		  try { sendOSCData(); }
		    	  	  catch (Exception e) {}
		    	  }
		    	  try { Thread.sleep(1000/60); }
		    	  catch (Exception e) {}
		      }
		    }
		}).start();
    	
    	
    }
	
    
    @Override
    public void onDestroy(){
    	running = false;
    	mHandler.removeMessages(0);
    	super.onDestroy();
    }
	
}
