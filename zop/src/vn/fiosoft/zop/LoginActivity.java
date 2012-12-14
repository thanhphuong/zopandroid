package vn.fiosoft.zop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener {

	private Button mLoginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mLoginButton = (Button) findViewById(R.id.loign);
		
		mLoginButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.loign) {
			
		}

	}
}
