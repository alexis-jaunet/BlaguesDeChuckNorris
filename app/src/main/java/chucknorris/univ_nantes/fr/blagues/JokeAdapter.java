package chucknorris.univ_nantes.fr.blagues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by E173246L on 08/12/17.
 */

public class JokeAdapter extends ArrayAdapter<Joke> {
    private int itemLayout;
    private LayoutInflater li;

    public JokeAdapter (Context context, int ressourceID) {
        super(context, ressourceID);

        this.itemLayout = ressourceID;
        this.li = LayoutInflater.from(context);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = this.li.inflate(this.itemLayout, null);
        }

        Joke j = this.getItem(position);
        if (j != null) {
            TextView tv = (TextView) view.findViewById(R.id.textView_blague);
            tv.setText (j.getBlague());
        }
        return view;
    }
}
