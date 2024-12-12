package com.example.car_request2.ui

enum class DriveDistance(
    val distance: Int,
    val id: Int
){
    Homer(1,1),
    Dominic (5,2),
    James  (10,3);

    companion object {

        fun distanceByPermision(id: Int, distance: Double): Boolean{
            return when (id) {
                Homer.id -> distance >= Homer.distance
                Dominic.id -> distance >= Dominic.distance
                James.id -> distance >= James.distance
                else -> false
            }
        }
    }
}