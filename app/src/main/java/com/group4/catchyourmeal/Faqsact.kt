package com.group4.catchyourmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Faqsact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_faqsact)

        var count1=1
        var count2=1
        var count3=1
        var count4=1
        var count5=1
        var count6=1
        var count7=1
        var count8=1
        var count9=1
        var count10=1
        var count11=1


        var Question1: TextView =findViewById(R.id.qus1)
        var Answer1: TextView =findViewById(R.id.ans1)
        var Question2: TextView =findViewById(R.id.qus2)
        var Answer2: TextView =findViewById(R.id.ans2)
        var Question3: TextView =findViewById(R.id.qus3)
        var Answer3: TextView =findViewById(R.id.ans3)
        var Question4: TextView =findViewById(R.id.qus4)
        var Answer4: TextView =findViewById(R.id.ans4)
        var Question5: TextView =findViewById(R.id.qus5)
        var Answer5: TextView =findViewById(R.id.ans5)
        var Question6: TextView =findViewById(R.id.qus6)
        var Answer6: TextView =findViewById(R.id.ans6)
        var Question7: TextView =findViewById(R.id.qus7)
        var Answer7: TextView =findViewById(R.id.ans7)
        var Question8: TextView =findViewById(R.id.qus8)
        var Answer8: TextView =findViewById(R.id.ans8)
        var Question9: TextView =findViewById(R.id.qus9)
        var Answer9: TextView =findViewById(R.id.ans9)
        var Question10: TextView =findViewById(R.id.qus10)
        var Answer10: TextView =findViewById(R.id.ans10)
        var Question11: TextView =findViewById(R.id.qus11)
        var Answer11: TextView =findViewById(R.id.ans11)


        Question1.setOnClickListener(){
            count1++
            if(count1%2==0) {
                Answer1.visibility = View.VISIBLE
            } else{
                Answer1.visibility= View.GONE
            } }
        Question2.setOnClickListener(){
            count2++
            if(count2%2==0) {
                Answer2.visibility = View.VISIBLE
            } else{
                Answer2.visibility= View.GONE
            } }
        Question3.setOnClickListener(){
            count3++
            if(count3%2==0) {
                Answer3.visibility = View.VISIBLE
            } else{
                Answer3.visibility= View.GONE
            } }
        Question4.setOnClickListener(){
            count4++
            if(count4%2==0) {
                Answer4.visibility = View.VISIBLE
            } else{
                Answer4.visibility= View.GONE
            } }
        Question5.setOnClickListener(){
            count5++
            if(count5%2==0) {
                Answer5.visibility = View.VISIBLE
            } else{
                Answer5.visibility= View.GONE
            } }
        Question6.setOnClickListener(){
            count6++
            if(count6%2==0) {
                Answer6.visibility = View.VISIBLE
            } else{
                Answer6.visibility= View.GONE
            } }
        Question7.setOnClickListener(){
            count7++
            if(count7%2==0) {
                Answer7.visibility = View.VISIBLE
            } else{
                Answer7.visibility= View.GONE
            } }
        Question8.setOnClickListener(){
            count8++
            if(count8%2==0) {
                Answer8.visibility = View.VISIBLE
            } else{
                Answer8.visibility= View.GONE
            } }
        Question9.setOnClickListener(){
            count9++
            if(count9%2==0) {
                Answer9.visibility = View.VISIBLE
            } else{
                Answer9.visibility= View.GONE
            } }
        Question10.setOnClickListener(){
            count10++
            if(count10%2==0) {
                Answer10.visibility = View.VISIBLE
            } else{
                Answer10.visibility= View.GONE
            } }
        Question11.setOnClickListener(){
            count11++
            if(count11%2==0) {
                Answer11.visibility = View.VISIBLE
            } else{
                Answer11.visibility= View.GONE
            } }

    }
}
