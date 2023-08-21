package com.example.retrofit_kotlin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_kotlin.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() , UserAdapter.OnclickSua {
    private lateinit var binding: ActivityMainBinding
        var BASE_URL : String = "https://63dc0253c45e08a043532d8a.mockapi.io/"
    lateinit var rcv : RecyclerView
    lateinit var myAdapter: UserAdapter
    var dto : DTO = DTO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rcvList.layoutManager = LinearLayoutManager(this)

        getData()

        putInfo("https://63dc0253c45e08a043532d8a.mockapi.io/sanpham")

//        binding.btnThem.setOnClickListener {
//
//
//            dto.name = binding.edName.text.toString()
//
//            Log.d("TAG", "onCreate: "+dto.name)
//
//            putData()
////            binhLuanDTO.setId_truyen(getid)
////
////            Log.d("zzzz", "onClick:id " + userDTO.getId())
////            binhLuanDTO.setId_user(userDTO)
//        }

    }
    // put dữ liệu bằng retrolfit
    private fun putData() {

        var Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface :: class.java)
        var retrodata = Retrofit.putData(dto)
        retrodata.enqueue(object : Callback<DTO>{
            override fun onResponse(call: Call<DTO>, response: Response<DTO>) {

                getData()

                Toast.makeText(
                    this@MainActivity,
                    "Them thành công",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "onFailure: lokkkkkkkk")
            }
            override fun onFailure(call: Call<DTO>, t: Throwable) {
                Log.d("TAG", "onFailure: loiiiiiiii")
            }

        })
    }

    private fun getData() {

        var Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface :: class.java)

        var retrodata = Retrofit.getData()
        retrodata.enqueue(object : Callback<List<DTO>>{
            override fun onResponse(call: Call<List<DTO>>, response: Response<List<DTO>>) {

              var data = response.body()
                myAdapter = UserAdapter(baseContext,data!!,this@MainActivity)
                binding.rcvList.adapter = myAdapter

            }
            override fun onFailure(call: Call<List<DTO>>, t: Throwable) {
            }

        })
    }

    
    // put dữ liệu bằng http
    fun putInfo(api_link: String) {

        binding.btnThem.setOnClickListener {
//            val dialog1 = Dialog(this@MainActivity)
//            dialog1.setContentView(R.layout.layout_dialog_add)
//            dialog1.setCancelable(false)
//            val window = dialog1.window
//            window!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            if (dialog1 != null && dialog1.window != null) {
//                dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//            }
//            val ed_name = dialog1.findViewById<EditText>(R.id.ed_name)
//            val ed_color = dialog1.findViewById<EditText>(R.id.ed_color)
//            val ed_price = dialog1.findViewById<EditText>(R.id.ed_price)
//            val btn_them = dialog1.findViewById<Button>(R.id.btn_them)
//            val huy = dialog1.findViewById<Button>(R.id.btn_huy)
//            btn_them.setOnClickListener {
                val service = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                service.execute {
                    try {
                        val url = URL(api_link)
                        // mở kết nối
                        val conn = url.openConnection() as HttpURLConnection
                        //Thiết lập phương thức POST, mặc định là GET
                        conn.requestMethod = "POST"
                        //tạo đối tượng dữ liệu để gửi lên server
                        val postData = JSONObject()
                        postData.put("name", binding.edName.text.toString())
//                        postData.put("price", ed_price.text.toString())
//                        postData.put("color", ed_color.text.toString())

                        //thiết lập kiểu dữ liệu sẽ gửi lên server
                        conn.setRequestProperty("Content-Type", "application/json")

                        // Tạo đối tượng out dữ liệu ra khỏi ứng đụng để gửi lên server
                        val outputStream = conn.outputStream
                        val writer =
                            BufferedWriter(OutputStreamWriter(outputStream))
                        //ghi dữ liệu vào outPut
                        writer.append(postData.toString())

                        //xóa bộ đệm
                        writer.flush()
                        writer.close()
                        outputStream.close()
                        //chưa cần đóng connection: hãy thu nhận dữ liệu mà server phản hồi
                        val inputStream = conn.inputStream
                        val reader =
                            BufferedReader(InputStreamReader(inputStream))
                        val builder = StringBuilder()
                        var dong: String?
                        while (reader.readLine().also { dong = it } != null) {
                            builder.append(dong).append("/n")
                        }
                        reader.close()
                        inputStream.close()
                        conn.disconnect()
                        val phanhoi = builder.toString()

                        // gửi dữ liệu phản hồi lên webview để xem
                        handler.post {
                            Toast.makeText(
                                this@MainActivity,
                                "Thêm Thành Công",
                                Toast.LENGTH_SHORT
                            ).show()
                     getData()
                        }
                    } catch (e: MalformedURLException) {
                        throw RuntimeException(e)
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    } catch (e: JSONException) {
                        throw RuntimeException(e)
                    }
                }
//                dialog1.dismiss()
            }
//            huy.setOnClickListener { dialog1.dismiss() }
//            dialog1.show()
        }

    override fun Clicksua(dto: DTO) {
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Thông Báo")
            .setMessage("Bạn có muốn xóa truyện '" + dto.name + "'")
            .setPositiveButton("Xóa") { dialogInterface, i ->
                var Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface :: class.java)

                var retrodata = Retrofit.delData(dto.id.toString())
                retrodata.enqueue(object : Callback<DTO>{
                    override fun onResponse(call: Call<DTO>, response: Response<DTO>) {


                    }
                    override fun onFailure(call: Call<DTO>, t: Throwable) {
                    }

                })

                getData()

            }
            .setNegativeButton("Hủy", null)
            .show()

    }

}
