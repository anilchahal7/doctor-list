package `in`.iceberg.data.store

import javax.inject.Inject

class DoctorsDataStoreFactory @Inject constructor(val doctorsRemoteDataStore: DoctorsRemoteDataStore)