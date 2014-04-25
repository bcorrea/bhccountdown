package bhctecnologia.bhccountdown;

import java.util.concurrent.TimeUnit;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	static NumberPicker pickerHour;
	static NumberPicker pickerMinute;
	static NumberPicker pickerSecond;
	static TextView lblTime;
	CountDownTimer timer1;
	//Time t;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
          
            pickerHour = (NumberPicker) rootView.findViewById(R.id.numberHour);
            pickerMinute = (NumberPicker) rootView.findViewById(R.id.numberMinute);
            pickerSecond = (NumberPicker) rootView.findViewById(R.id.numberSecond);
            lblTime = (TextView) rootView.findViewById(R.id.lblTime);
            
            pickerHour.setFormatter(myFORMATTER);
            pickerHour.setMinValue(0);
            pickerHour.setMaxValue(99);
            
            pickerMinute.setFormatter(myFORMATTER);
            pickerMinute.setMinValue(0);
            pickerMinute.setMaxValue(59);
            
            pickerSecond.setFormatter(myFORMATTER);
            pickerSecond.setMinValue(0);
            pickerSecond.setMaxValue(59);

            return rootView;
        }
    }

    public void btnStart_Click(View view) {
    	int iHour = pickerHour.getValue();
    	int iMinute = pickerMinute.getValue();
    	int iSecond = pickerSecond.getValue();
    	long lTotalTime = GetTimerBase(iHour, iMinute, iSecond);
    	
    	timer1 = new CountDownTimer(lTotalTime, 1000){
			@Override
			public void onFinish() {
				lblTime.setText("Done!");
			}

			@Override
			public void onTick(long arg0) {
				lblTime.setText(getTimeString(arg0));
			};
    	};
    	timer1.start();
    }
    public void btnReset_Click(View view) {
    	
    }
    public void btnStop_Click(View view){
    	timer1.cancel();
    }
    public void btnSet_Click(View view){
    	int iHour = pickerHour.getValue();
    	int iMinute = pickerMinute.getValue();
    	int iSecond = pickerSecond.getValue();
    	long lTotalTime = GetTimerBase(iHour, iMinute, iSecond);
    	lblTime.setText(getTimeString(lTotalTime));
    }
    
    private static Formatter myFORMATTER =
            new Formatter() {
				
				@Override
				public String format(int value) {
					mArgs[0] = value;
                    mBuilder.delete(0, mBuilder.length());
                    mFmt.format("%02d", mArgs);
                    return mFmt.toString();
				}
				
				final StringBuilder mBuilder = new StringBuilder();
                final java.util.Formatter mFmt = new java.util.Formatter(
                        mBuilder, java.util.Locale.US);
                final Object[] mArgs = new Object[1];
        };
    
    private static String getTimeString(long milliSeconds)
    {
    	int seconds = (int) (milliSeconds / 1000) % 60 ;
    	int minutes = (int) ((milliSeconds / (1000*60)) % 60);
    	int hours   = (int) ((milliSeconds / (1000*60*60)));
    	
		return String.format("%02d:%02d:%02d",
				hours,
				minutes,
    		    seconds);
    }
    
    private static long GetTimerBase(int iHour, int iMinute, int iSecond){
    	return TimeUnit.MILLISECONDS.convert(iHour, TimeUnit.HOURS) + 
    	    	TimeUnit.MILLISECONDS.convert(iMinute, TimeUnit.MINUTES) + 
    	    	TimeUnit.MILLISECONDS.convert(iSecond, TimeUnit.SECONDS);
    }
}
