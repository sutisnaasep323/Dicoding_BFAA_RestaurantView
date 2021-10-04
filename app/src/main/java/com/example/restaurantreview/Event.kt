package com.example.restaurantreview
/*
T adalah tipe generic yang bisa digunakan supaya kelas ini dapat membungkus berbagai macam data.
Data yang dibungkus tersebut kemudian akan dimasukkan ke dalam variabel content
 */
open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    /*
    ini adalah fungsi utama, Fungsi tersebut akan memeriksa apakah aksi ini pernah dieksekusi
    sebelumnya. Caranya yaitu dengan memanipulasi variabel hasBeenHandled
     */
    fun getContentIfNotHandled(): T? {
        /*
        Awalnya variabel hasBeenHandled bernilai false. Kemudian ketika aksi pertama kali dilakukan
        nilai hasBeenHandled akan diubah menjadi true. Sedangkan pada aksi selanjutnya ia akan
        mengembalikan null karena hasBeenHandled telah bernilai true
         */
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}