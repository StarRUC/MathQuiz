package edu.sjsu.starruc.mathquiz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KeyboardFragment.KeyboardListener} interface
 * to handle interaction events.
 * Use the {@link KeyboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeyboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    private KeyboardListener mListener;

    public KeyboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeyboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KeyboardFragment newInstance(String param1, String param2) {
        KeyboardFragment fragment = new KeyboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_keyboard, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {

        // Define Listener for Number Pad
        View.OnClickListener numberKeyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNumberPadInput(v);
            }
        };

        // Define Listener for Enter Key
        View.OnClickListener enterKeyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.pressEnter();
            }
        };

        view.findViewById(R.id.pad_key_0).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_1).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_2).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_3).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_4).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_5).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_6).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_7).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_8).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_9).setOnClickListener(numberKeyListener);
        view.findViewById(R.id.pad_key_enter).setOnClickListener(enterKeyListener);

    }


    private void getNumberPadInput(View v) {
        int digit = 0;

        switch (v.getId()) {
            case R.id.pad_key_0:
                digit = 0;
                break;
            case R.id.pad_key_1:
                digit = 1;
                break;
            case R.id.pad_key_2:
                digit = 2;
                break;
            case R.id.pad_key_3:
                digit = 3;
                break;
            case R.id.pad_key_4:
                digit = 4;
                break;
            case R.id.pad_key_5:
                digit = 5;
                break;
            case R.id.pad_key_6:
                digit = 6;
                break;
            case R.id.pad_key_7:
                digit = 7;
                break;
            case R.id.pad_key_8:
                digit = 8;
                break;
            case R.id.pad_key_9:
                digit = 9;
                break;
            default:
                digit = 0;
        }
        mListener.pressDigit(digit);

        return;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof KeyboardListener) {
            mListener = (KeyboardListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface KeyboardListener {
        // TODO: Update argument type and name
        void pressDigit(int digit);
        void pressEnter();
    }


}
