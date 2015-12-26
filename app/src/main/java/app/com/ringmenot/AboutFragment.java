package app.com.ringmenot;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
    TextView credits, about, title;
    final private String ABOUT = "Ring me not is a work-life balancing solution for corporate people," +
            "businessmen, entrepreneurs and any other who don't want to be engages during non-office " +
            "hours. This Application saves you from keeping two phones, or dual phones, rather a Ring-Me-NOT user" +
            "gets a VPN(Virtual Private Number) which he can give to his clients as his work Number. " +
            "During his work hours all the calls to this number are diverted to your one and only home number" +
            "without letting caller know. And during non-working you can set your status as busy so all the calls" +
            "would be forwarded to an answering module to narrate any custom message you want." +
            "Created By: Parminder Singh Sethi";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        credits = (TextView) rootView.findViewById(R.id.credits);
        about = (TextView) rootView.findViewById(R.id.about);
        title = (TextView) rootView.findViewById(R.id.title);

        Typeface helvetica = Typeface.createFromAsset(getActivity().getAssets(), "CaviarDreams.ttf");

        credits.setTypeface(helvetica);
        title.setTypeface(helvetica);
        about.setTypeface(helvetica);
        about.setText(ABOUT);
        return rootView;
    }
}
