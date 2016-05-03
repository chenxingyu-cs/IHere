package cmu.sv.flubber.ihere.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.entities.Comment;
import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.ui.HistoryActivity;
import cmu.sv.flubber.ihere.ui.ItagDetailActivity;
import cmu.sv.flubber.ihere.ui.ItagDetailFragment;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{
    private List<Comment> mValues;

    public CommentListAdapter(List<Comment> items) {
        mValues = items;
    }

    public void setmValues(List<Comment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_content, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CommentListAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mText.setText(mValues.get(position).getContent());
        holder.mUser.setText(mValues.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mText;
        public Comment mItem;
        public final TextView mUser;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUser = (TextView) view.findViewById(R.id.textUserName);
            mText = (TextView) view.findViewById(R.id.textViewItem);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mText.getText() + "'";
        }
    }
}
