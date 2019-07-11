//package com.myapplicationdev.android.project0044;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class deleteItemAdapter extends BaseAdapter {
//
//    private Context context;
//    private List<Category> listItems;
//    private List<Category> listItemsList;
//
//    private List<Category> listItemsSelected;//keep track of selected objects
//    private List<View> listSelectedRows;//keep track of selected rows
//
//    public deleteItemAdapter(Context context, List<String> listItem){
//        this.context = context;
//        this.listItems = listItems;
//        listItemsList = new ArrayList<>(listItems);
//    }
//
//    public void addItem(Category person){
//        listItems.add(person);
//        listPersonsFilter.add(person);
//    }
//
//    @Override
//    public int getCount() {
//        return listPersonsFilter.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if(convertView == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, null);
//
//            viewHolder = new ViewHolder();
//
//            viewHolder.imageViewProfilePic = convertView.findViewById(R.id.imageViewProfilePic);
//            viewHolder.textViewName = convertView.findViewById(R.id.textViewName);
//            viewHolder.textViewDescription = convertView.findViewById(R.id.textViewDescription);
//
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//        Person person = listPersonsFilter.get(position);
//
//        viewHolder.textViewName.setText(person.getFirstName() + " " + person.getLastName());
//        viewHolder.textViewDescription.setText(person.getDescription());
//        viewHolder.imageViewProfilePic.setImageDrawable(getImageDrawable(person.getImageName()));
//
//        return convertView;
//    }
//
//    public void filter(String text){
//        text = text.toLowerCase();
//        listPersonsFilter.clear();
//        if(text.length() == 0){
//            listPersonsFilter.addAll(listPersons);
//        }else{
//            String name;
//            for(Person person : listPersons){
//                name = (person.getFirstName() + " " + person.getLastName()).toLowerCase();
//                if(name.contains(text)){
//                    listPersonsFilter.add(person);
//                }
//            }
//        }
//
//        notifyDataSetChanged();
//    }
//
//    private Drawable getImageDrawable(String imageName){
//        int id  = context.getResources().getIdentifier(imageName, "drawable",
//                context.getPackageName());
//        return context.getResources().getDrawable(id);
//    }
//
//    class ViewHolder{
//        ImageView imageViewProfilePic;
//        TextView textViewName;
//        TextView textViewDescription;
//    }
//}
