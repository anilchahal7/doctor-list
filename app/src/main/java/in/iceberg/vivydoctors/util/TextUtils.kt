package `in`.iceberg.vivydoctors.util

class TextUtils private constructor() {
    init {
        throw UnsupportedOperationException("Cannot instantiate static holder.")
    }
    companion object {
        @JvmStatic
        fun isNotNullOrEmpty(string: String?): Boolean {
            return !string.isNullOrBlank()
        }
    }
}
