package vn.fiosoft.setting.accountmanage;

import java.util.List;

import vn.fiosoft.zop.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AccountManageActivity extends ListActivity implements View.OnClickListener {

	private ListView mListView;
	private List<Account> items;
	private Button mSignInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_account_manage);

		mSignInButton = (Button) findViewById(R.id.sign_in);

		mSignInButton.setOnClickListener(this);

		mListView = getListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshListAdapter();
	}

	public void refreshListAdapter() {

		AccountManage accountManage = new AccountManage(this);
		items = accountManage.list();

		mListView.setAdapter(new AccountManageAdapter(this, items));
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				Account item = items.get(position);
				Toast.makeText(AccountManageActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

			}
		});

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.sign_in) {
			startActivity(new Intent(this, SignInActivity.class));
		}

	}

}
