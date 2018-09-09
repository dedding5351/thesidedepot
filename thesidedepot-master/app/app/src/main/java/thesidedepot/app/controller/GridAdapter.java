package thesidedepot.app.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import thesidedepot.app.R;

public class GridAdapter extends BaseAdapter {
    Context context;
    private final String[] materials;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] materials) {
        this.context = context;
        this.materials = materials;
    }

    @Override
    public int getCount() {
        return materials.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item, null);
            TextView material = (TextView) view.findViewById(R.id.materialName);
            material.setText(materials[position]);

            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 250));
        }

        return view;
    }
}
