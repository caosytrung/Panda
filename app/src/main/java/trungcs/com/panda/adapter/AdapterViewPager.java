package trungcs.com.panda.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caotr on 16/09/2016.
 */
public class AdapterViewPager extends FragmentPagerAdapter {

    public static final String TOPVN = "bxh việt nam";
    public static final String TOPUS = "bxh Châu Mũ";
    public static final String TOPCHAUA = "bxh Châu Á";
    public static final String MYMUSIC = "Nhạc của tôi";
    List<Fragment> list = new ArrayList<>();
    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }


    public void addFragment( Fragment fragment){
        list.add(fragment);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
//        if(position == 0 ){
//            return new MyMusicFragment();
//
//        }
//        else{
//            if(position == 1){
//                return new TopUSFragment();
//            }
//            else {
//                if(position == 2){
//                    return new ToChauAFragment();
//                }
//                else {
//                    return new TopVnFragment();
//                }
//            }
//        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return MYMUSIC;
        } else {
            if (position == 1) {
                return TOPUS;
            } else {
                if (position == 2) {
                    return TOPCHAUA;
                } else {
                    return TOPVN;
                }
            }
        }
    }


}
