package com.npe.galaxyorganic.ui.model.db

data class PaymentModel(
    val id : Long?,
    val namaPenerima : String?,
    val emailPenerima : String?,
    val tanggalBeli : String?,
    val alamatPenerima : String?,
    val notelpPenerima : String?,
    val subTotal : String?,
    val diskon : String?,
    val totalharga : String?
) {
    companion object {
        const val TABLE_PAYMENT = "TABLE_PAYMENT"
        const val ID = "ID_"
        const val NAMA_PENERIMA = "NAMA_PENERIMA"
        const val EMAIL_PENERIMA = "EMAIL_PENERIMA"
        const val TANGGAL_BELI = "TANGGAL_BELI"
        const val ALAMAT_PENERIMA = "ALAMAT_PENERIMA"
        const val NOTELP_PENERIMA = "NOTELP_PENERIMA"
        const val SUBTOTAL = "SUBTOTAL"
        const val DISKON = "DISKON"
        const val TOTAL_HARGA ="TOTAL_HARGA"
    }
}