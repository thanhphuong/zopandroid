package vn.fiosoft.setting.accountmanage;

import vn.fiosoft.http.HttpConnection;
import vn.fiosoft.zop.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends Activity implements OnClickListener {
	
	private EditText mEmailEditText;
	private EditText mPasswordEditText;
	private Button mSignInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		mEmailEditText = (EditText) findViewById(R.id.email);
		mPasswordEditText = (EditText) findViewById(R.id.password);
		mSignInButton = (Button) findViewById(R.id.sign_in);

		mSignInButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.sign_in) {
			String email = mEmailEditText.getText().toString().trim();
			String password = mPasswordEditText.getText().toString().trim();
			if (email.equals("") || password.equals("")) {
				Toast.makeText(this, "Email or Password is not empty", Toast.LENGTH_SHORT).show();
				return;
			}
			
			HttpConnection connection = new HttpConnection();
			int result = connection.login(email, password);
			if (result > 0){
				Account account  = new Account(result,email, Account.SYNC_ON);
				AccountManage accountManage = new AccountManage(this);
				accountManage.setAccountSync(account);
				finish();
				return;
			}
			
			Toast.makeText(this, String.valueOf(result), Toast.LENGTH_SHORT).show();

		}

	}
}
