package edu.sjsu.starruc.mathquiz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    int quizType = 0;

    View view;
    TextView txtQid, txtOpnd1, txtOpnd2, txtOptr;
    EditText ansEditTxt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init() {
        // Get widget objs
        txtQid = (TextView) view.findViewById(R.id.question_id_txt);
        txtOpnd1 = (TextView) view.findViewById(R.id.opnd1_txt);
        txtOpnd2 = (TextView) view.findViewById(R.id.opnd2_txt);
        txtOptr = (TextView) view.findViewById(R.id.optr_txt);
        ansEditTxt = (EditText) view.findViewById(R.id.ans_txt);
    }

    public void setQuestionView(int questionId, int opnd1, int opnd2, int quizType)
    {
        txtQid.setText("Question " + questionId + "/10");
        txtOpnd1.setText(String.valueOf(opnd1));
        txtOpnd2.setText(String.valueOf(opnd2));
        switch (quizType) {
            case 1:
                txtOptr.setText("+");
                break;
            case 2:
                txtOptr.setText("-");
                break;
            case 3:
                txtOptr.setText("*");
                break;
            default:
                break;
        }

        ansEditTxt.setText("");

    }

    public void setAnswerText(String s) {
        ansEditTxt.setText(s);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_question, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        init();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
