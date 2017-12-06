package neetiayog.samarthgupta.com.neetiayog;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static String stateLists = "Andhra Pradesh, " +
            "Arunachal Pradesh, " +
            "Assam, " +
            "Bihar, " +
            "Goa, " +
            "Gujarat, " +
            "Haryana – Chandigarh\n" +
            "Himachal Pradesh – Shimla\n" +
            "Jammu & Kashmir – Srinagar (Winter : Jammu)\n" +
            "Karnataka – Bangalooru\n" +
            "Kerala – Thiruvananthapuram\n" +
            "Madhya Pradesh – Bhopal\n" +
            "Maharashtra – Mumbai\n" +
            "Manipur – Imphal\n" +
            "Meghalaya – Shillong\n" +
            "Mizoram – Aizawl\n" +
            "Nagaland – Kohima\n" +
            "Orissa – Bhubaneswar\n" +
            "Punjab – Chandigarh\n" +
            "Rajasthan – Jaipur\n" +
            "Sikkim – Gangtok\n" +
            "Tamil Nadu – Chennai\n" +
            "Tripura – Agartala\n" +
            "Uttar Pradesh – Lucknow\n" +
            "West Bengal – Kolkata\n" +
            "Chhattisgarh – Raipur\n" +
            "Uttarakhand – Dehradun\n" +
            "Jharkhand – Ranchi, "+"Delhi, ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng india = new LatLng(21.146633, 79.088860);
        CameraPosition pos = new CameraPosition.Builder().target(india).zoom(4).build();
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latLng.latitude+","+latLng.longitude+"&key=AIzaSyDr7gz8Cajvtk5o1W7qYRzeQOShAHbUStI";
                String url2 = "https://raw.githubusercontent.com/samarthgupta1011/NeetiAyog/master/app/src/main/java/neetiayog/samarthgupta/com/neetiayog/data.json";

                Log.d("TAGG",latLng.latitude+"  "+latLng.longitude);
                Volley.newRequestQueue(MapsActivity.this).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray main = obj.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
                            String state;
                            for(int i =0; i<main.length();i++){
                                if(stateLists.contains(main.getJSONObject(i).getString("long_name"))){
                                    state = main.getJSONObject(i).getString("long_name");
                                    Log.d("STATE",state);
                                    break;

                                }

                            }



//                            String state = main.getString("long_name");



//                            if(stateLists.contains(state)){
//
//                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
            }
        });

    }


}
