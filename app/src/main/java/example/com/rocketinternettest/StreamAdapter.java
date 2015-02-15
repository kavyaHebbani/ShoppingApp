package example.com.rocketinternettest;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import example.com.rocketinternettest.images.ImageLoader;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class StreamAdapter extends BaseAdapter {

    static float leftRange = FilterDialog.leftValueDefault;
    static float rightRange = FilterDialog.rightValueDefault;
    ViewHolder viewHolder = new ViewHolder();
    Context context;
    List<Stream> filterElements;
    List<Stream> elements;
    ImageLoader imageLoader;

    public StreamAdapter(Context context, List<Stream> elements) {
        this.context = context;
        this.elements = new ArrayList<>(elements);
        filterElements = new ArrayList<>();
        filterElements.addAll(this.elements);
        imageLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (filterElements == null || filterElements.isEmpty()) {
            convertView = inflater.inflate(R.layout.no_data, parent, false);
            return convertView;
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.stream_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.brand = (TextView) convertView.findViewById(R.id.brand);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(filterElements.get(position).getName());
        viewHolder.brand.setText(filterElements.get(position).getBrand());
        viewHolder.price.setText(filterElements.get(position).getPrice());
        imageLoader.DisplayImage(filterElements.get(position).getImageUrl(), viewHolder.image);
        return convertView;
    }

    @Override
    public int getCount() {
        if (filterElements.size() == 0) {
            return 1;
        }
        return filterElements.size();
    }

    @Override
    public Stream getItem(int position) {
        return filterElements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setElements(List<Stream> elements) {
        this.elements = elements;
        filterElements.clear();
        if (this.elements != null) {
            filterElements.addAll(this.elements);
        }
        filter();
        sort();
    }

    public void sort() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("RIAndroidTest", Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt("sortChoice", 0)) {
            case 0:
                Collections.sort(filterElements, StreamComparator.PRICE_ASC);
                break;
            case 1:
                Collections.sort(filterElements, StreamComparator.PRICE_DESC);
                break;
            case 2:
                Collections.sort(filterElements, StreamComparator.NAME_ASC);
                break;
            case 3:
                Collections.sort(filterElements, StreamComparator.NAME_DESC);
                break;
        }
        notifyDataSetChanged();
    }

    public void filter() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("RIAndroidTest", Context.MODE_PRIVATE);
        leftRange = sharedPreferences.getInt("filterLeft", FilterDialog.leftValueDefault);
        rightRange = sharedPreferences.getInt("filterRight", FilterDialog.rightValueDefault);
        filterElements.clear();
        for (Stream stream : elements) {
            if ((leftRange <= Float.valueOf(stream.getPrice())) && Float.valueOf(stream.getPrice()) <= rightRange) {
                filterElements.add(stream);
            }
        }
        sort();
    }

    enum StreamComparator implements Comparator<Stream> {
        NAME_ASC {
            public int compare(Stream o1, Stream o2) {
                return o1.getName().compareTo(o2.getName());
            }
        },
        NAME_DESC {
            public int compare(Stream o1, Stream o2) {
                return o2.getName().compareTo(o1.getName());
            }
        },
        PRICE_ASC {
            public int compare(Stream o1, Stream o2) {
                return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
            }
        },
        PRICE_DESC {
            public int compare(Stream o1, Stream o2) {
                return Float.valueOf(o2.getPrice()).compareTo(Float.valueOf(o1.getPrice()));
            }
        }
    }

    static class ViewHolder {
        TextView name;
        TextView price;
        TextView brand;
        ImageView image;
    }
}