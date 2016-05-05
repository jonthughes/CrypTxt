package uw.edu.tcss450.team6.cryptxt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uw.edu.tcss450.team6.cryptxt.MsgListFragment.OnListFragmentInteractionListener;
import uw.edu.tcss450.team6.cryptxt.model.Msg;

import java.util.List;

/**
 * MyMsgRecyclerViewAdapter is the view adapter for the inbox.
 *
 * @author Jonathan Hughes
 * @date 28 April 2016
 *
 * {@link RecyclerView.Adapter} that can display a {@link Msg} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyMsgRecyclerViewAdapter extends RecyclerView.Adapter<MyMsgRecyclerViewAdapter.ViewHolder> {

    private final List<Msg> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMsgRecyclerViewAdapter(List<Msg> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_msg, parent, false);
        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getSender());
        holder.mContentView.setText(mValues.get(position).getDtg());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Holds the inbox view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Msg mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
