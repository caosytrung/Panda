package trungcs.com.panda.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import trungcs.com.panda.R;
import trungcs.com.panda.interface_callback.ISearch;

/**
 * Created by caotr on 21/09/2016.
 */
public class SearchDialog extends Dialog  {
    private EditText mEdtSearch;
    private Button mBtnSearch;
    private ImageView mIvBack;
    private ISearch mISearch;


    public SearchDialog(Context context,ISearch iSearch) {
        super(context);
        mISearch = iSearch;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_search);
        getWindow().setBackgroundDrawable(null);
        initViews();

    }

    private void initViews(){
        mBtnSearch = (Button) findViewById(R.id.btn_seerch_dialog1);
        mEdtSearch = (EditText) findViewById(R.id.edt_search_dialog1);
        mIvBack = (ImageView) findViewById(R.id.iv_back_dialog1);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String contentSearch = mEdtSearch.getText().toString();
                mISearch.search(contentSearch);
            }
        });

    }

}
