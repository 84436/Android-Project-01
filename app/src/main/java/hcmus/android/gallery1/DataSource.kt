package hcmus.android.gallery1
import hcmus.android.gallery1.models.photo;

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<photo>{
            val list = ArrayList<photo>()
            list.add(
                    photo(

                            "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",

                            )
            )
            list.add(
                    photo(


                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/time_to_build_a_kotlin_app.png",

                            )
            )

            list.add(
                    photo(

                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/coding_for_entrepreneurs.png",

                            )
            )
            list.add(
                    photo(
                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/freelance_android_dev_vasiliy_zukanov.png",

                            )
            )
            list.add(
                    photo(
                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/freelance_android_dev_donn_felker.png",

                            )
            )
            list.add(
                    photo(
                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/work_life_balance.png",

                            )
            )
            list.add(
                    photo(
                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/fullsnack_developer.png",

                            )
            )
            list.add(
                    photo(
                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/javascript_expert_wes_bos.png",

                            )
            )
            list.add(
                    photo(
                            "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/senior_android_engineer_kaushik_gopal.png",

                            )
            )
            return list
        }
    }
}