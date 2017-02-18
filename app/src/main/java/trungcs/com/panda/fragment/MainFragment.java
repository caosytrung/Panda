package trungcs.com.panda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;

import java.util.List;

import trungcs.com.panda.R;
import trungcs.com.panda.activity.HomeActivity;
import trungcs.com.panda.adapter.AdapterViewPager;
import trungcs.com.panda.interface_callback.IDataMedia;
import trungcs.com.panda.models.AudioPlayer;

/**
 * Created by caotr on 21/09/2016.
 */
public class MainFragment extends Fragment implements IDataMedia {
    private static final String TAG ="MainFragment" ;
    private ImageView mIvSearchOnline;
    private View.OnClickListener mOnClickListener;
    FragmentManager fg;
    public MainFragment(FragmentManager fragmentManager, View.OnClickListener onClickListener){
        fg = fragmentManager;
        mOnClickListener = onClickListener;

    }
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AdapterViewPager mAdapterViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onPause/ " + "dapause");
        View view = inflater.inflate(
                R.layout.fragment_1,
                container,false
        );
        mIvSearchOnline = (ImageView) view.findViewById(R.id.iv_searchnhac);
        mIvSearchOnline.setOnClickListener(mOnClickListener);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_head);
        mViewPager = (ViewPager) view.findViewById(R.id.pager_body);
        mAdapterViewPager = new AdapterViewPager(fg);
        addViewPager(mAdapterViewPager);
        mViewPager.setAdapter(mAdapterViewPager);
        mViewPager.setPageTransformer(true, new CubeOutTransformer());
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapterViewPager.notifyDataSetChanged();
        return view;
    }
    private void addViewPager(AdapterViewPager adapterViewPager) {
        adapterViewPager.addFragment(new MyMusicFragment(this));
        adapterViewPager.addFragment(new TopUSFragment(this));
        adapterViewPager.addFragment(new ToChauAFragment(this));
        adapterViewPager.addFragment(new TopVnFragment(this));
    }
    @Override
    public void getData(int pos, List<AudioPlayer> list, boolean isOnline) {
        HomeActivity mainActivity = (HomeActivity)getActivity();
        mainActivity.start(pos,list,isOnline);
    }
    @Override
    public void onPause() {
        super.onPause();

    }

}