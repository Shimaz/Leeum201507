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
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
	private String PACKAGE_NAME;
	private ArrayList<ImageView> btnImg;
//	private ArrayList<ImageView> btnOverImg;
	private ArrayList<ImageView> lineImg;
// 	private ArrayList<ImageView> lineOverImg;	
	private ArrayList<ImageView> overlayImg;

	private Button btnKor, btnEng;
	
	
	private boolean running;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		running = true;
		sendPeriodicUpdates = true;
		
		SharedPreferences setting = this.getPreferences(MODE_PRIVATE);	
		oscIP = setting.getString("myIP", "192.168.0.3");
		oscPort = setting.getInt("myPort", 3334);
		drawAdditionalInfo = setting.getBoolean("extraInfo", true);
		
		oscInterface = new OSCInterface(oscIP, oscPort);
		
		screenNumber = 0;
		language = 0;
		
		btnImg = new ArrayList<ImageView>();
		lineImg = new ArrayList<ImageView>();
		overlayImg = new ArrayList<ImageView>();
		
		PACKAGE_NAME = getApplicationContext().getPackageName();
		
		isTouched = false;
		
		// 새 액티비티를 열지 않고 이곳에서 ImageView의 애니메이션을 사용. 
		// 각 버튼을 터치하거나 할 때 OSCInterface를 통해 통신 
		Button btn01, btn02, btn03, btn04, btn05;
		
		
		btn01 = (Button)findViewById(R.id.main_btn01);
		btn02 = (Button)findViewById(R.id.main_btn02);
		btn03 = (Button)findViewById(R.id.main_btn03);
		btn04 = (Button)findViewById(R.id.main_btn04);
		btn05 = (Button)findViewById(R.id.main_btn05);
		btnKor = (Button)findViewById(R.id.main_btnKor);
		btnEng = (Button)findViewById(R.id.main_btnEng);
		
		
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
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{setButtonStatus();}
					
				}
				
				
			}
		});
		
		
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
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{setButtonStatus();}
					
				}
			}
		});
		
		
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
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{setButtonStatus();}
					
				}
			}
		});
		
		
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
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{setButtonStatus();}
					
				}
				
			}
		});
		
		
		btn05.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}

				
				if(screenNumber != 5){
					screenNumber = 5;
					
					setButtonStatus();
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{setButtonStatus();}
					
				}
			}
		});
		
		
		
		btnKor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}

				
				if(language != 0){
					language = 0;
					setButtonStatus();
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{ setButtonStatus();}
					
				}
				
				
			}
		});
		
		btnEng.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isTouched){
					isTouched = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}else{
					tCounter = 0;
				}

				
				if(language != 1){
					language = 1;
					
					setButtonStatus();
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error", e.toString());}
//					finally{ setButtonStatus();}
					}
				
				
			}
		});

			
	
		

		
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
					language = 0;
					isTouched = false;
					
					
					setButtonStatus();
					
//					try{ sendOSCData(); }
//					catch(Exception e){ android.util.Log.i("error: ", e.toString()); }
//					finally{ setButtonStatus(); };
				}
			
				
			}
			
			
		};
		
		
		
		
		// add button imageviews to array
		for(int i = 0; i < 5; i++){
			String str = String.format("img_btn%02d", i+1);
			int rid = getResources().getIdentifier(str, "id", PACKAGE_NAME);
			ImageView iv = (ImageView)findViewById(rid);
			btnImg.add(iv);
		}
		
		for(int i = 0; i < 5; i++){
			String str = String.format("img_line%02d", i+1);
			int rid = getResources().getIdentifier(str, "id", PACKAGE_NAME);
			ImageView iv = (ImageView)findViewById(rid);
			lineImg.add(iv);
		}
		
		for(int i = 0; i < 5; i++){
			String str = String.format("img_overlay%02d", i+1);
			int rid = getResources().getIdentifier(str, "id", PACKAGE_NAME);
			ImageView iv = (ImageView)findViewById(rid);
			overlayImg.add(iv);
		}
		
		setButtonStatus();
		
		startOSC();
		
//		try{ sendOSCData(); }
//		catch(Exception e){ android.util.Log.i("error", e.toString());}
//		finally{setButtonStatus();}
		
		
		
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
		
		
		// TODO 버튼 애니메이션 처리 
		for(int i = 0; i < 5; i++){
			
			ImageView iv = btnImg.get(i);
			if(screenNumber == i+1){
				iv.setImageResource(R.drawable.info_01_btn_down);
			}else{
				iv.setImageResource(R.drawable.info_01_btn_up);
			}
		}
	
		
		// TODO 라인 애니메이션 처리 
		for(int i = 0; i < 5; i++){
			
			ImageView iv = lineImg.get(i);
			
			if(screenNumber == i+1){
				String str = String.format("info_01_img_line_%02d_down", i+1);
				int rid = getResources().getIdentifier(str, "drawable", PACKAGE_NAME);
				iv.setImageResource(rid);
			}else{
				String str = String.format("info_01_img_line_%02d_up", i+1);
				int rid = getResources().getIdentifier(str, "drawable", PACKAGE_NAME);
				iv.setImageResource(rid);
			}
		}
		
		
		// TODO Overlay 애니메이션 처리 
		
		for(int i = 0; i < 5; i++){
			
			ImageView iv = overlayImg.get(i);
			
			if(screenNumber == i+1){
				iv.setAlpha(1.0f);
			}else{
				iv.setAlpha(0.0f);
			}
		}
		
		// TODO 언어버튼 이미지 토글 처리 
		if(language == 0){
			
			btnKor.setBackgroundResource(R.drawable.info_01_img_kor_on);
			btnEng.setBackgroundResource(R.drawable.info_01_img_eng_off);
			
		}else{
			
			btnKor.setBackgroundResource(R.drawable.info_01_img_kor_off);
			btnEng.setBackgroundResource(R.drawable.info_01_img_eng_on);
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
		
		
//		android.util.Log.i("osc ", outputData.toString());
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
