package vn.fiosoft.gps;

import java.util.ArrayList;

import vn.fiosoft.http.HttpConnection;
import vn.fiosoft.zop.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem>
{

    private Context mContext;
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

    public MapItemizedOverlay(Drawable defaultMarker)
    {
        super(boundCenterBottom(defaultMarker));
    }
    
    public MapItemizedOverlay(Drawable defaultMarker, Context context)
    {
        super(boundCenterBottom(defaultMarker));
        mContext = context;
    }

    public void addOverlay(OverlayItem overlay)
    {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i)
    {
        return mOverlays.get(i);
    }

    @Override
    public int size()
    {
        return mOverlays.size();
    }
    
    @Override
    protected boolean onTap(int index) {
      final OverlayItem item = mOverlays.get(index);
      AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);		
		dialog.setItems(R.array.share, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {
				HttpConnection connection = new HttpConnection();
				//int result = connection.sendLocation(1001, item .getTime(), point.getLatitudeE6()/1E6, point.getLongitudeE6()/1E6);
				
			}
		});
		dialog.show();
      return true;
    }
   
 
}
