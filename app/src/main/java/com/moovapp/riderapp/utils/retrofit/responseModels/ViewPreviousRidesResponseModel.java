package com.moovapp.riderapp.utils.retrofit.responseModels;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lijo Mathew Theckanal on 25-Jul-18.
 */

public class ViewPreviousRidesResponseModel implements Serializable{


    /**
     * status : true
     * message : Rides Lists
     * count : 64
     * data : [{"ride_id":119,"ride_trip_id":33,"ride_driver_id":10,"ride_booked_on":"2018-07-26 05:52:15","ride_booked_on_date":"2018-07-26","ride_booked_on_time":"05:52:15","ride_amount":"250","ride_status":"completed","ride_payment_status":"paid","ride_payment_time":"2018-07-26 18:22:15","ride_seats":2,"ride_from":" kanyakumari","ride_to":"kovalam","driver_data":null},{"ride_id":118,"ride_trip_id":33,"ride_driver_id":10,"ride_booked_on":"2018-07-26 06:06:46","ride_booked_on_date":"2018-07-26","ride_booked_on_time":"06:06:46","ride_amount":"250","ride_status":"completed","ride_payment_status":"paid","ride_payment_time":"2018-07-26 18:36:46","ride_seats":2,"ride_from":" Trivandum","ride_to":"kayankulam","driver_data":null},{"ride_id":116,"ride_trip_id":10,"ride_driver_id":31,"ride_booked_on":"2018-07-24 02:30:40","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"02:30:40","ride_amount":"250","ride_status":"completed","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":96,"ride_trip_id":96,"ride_driver_id":31,"ride_booked_on":"2018-07-24 15:14:19","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"15:14:19","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Nest Buildings, Technopark Campus, Thiruvananthapuram, Kerala 695581, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":95,"ride_trip_id":95,"ride_driver_id":31,"ride_booked_on":"2018-07-24 15:14:14","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"15:14:14","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Nest Buildings, Technopark Campus, Thiruvananthapuram, Kerala 695581, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":94,"ride_trip_id":94,"ride_driver_id":31,"ride_booked_on":"2018-07-24 15:14:09","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"15:14:09","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Nest Buildings, Technopark Campus, Thiruvananthapuram, Kerala 695581, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":93,"ride_trip_id":93,"ride_driver_id":31,"ride_booked_on":"2018-07-24 15:14:03","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"15:14:03","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Nest Buildings, Technopark Campus, Thiruvananthapuram, Kerala 695581, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":92,"ride_trip_id":92,"ride_driver_id":31,"ride_booked_on":"2018-07-24 14:55:08","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"14:55:08","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Kazhakkoottam,kerala,India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":91,"ride_trip_id":91,"ride_driver_id":31,"ride_booked_on":"2018-07-24 14:54:59","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"14:54:59","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Kazhakkoottam,kerala,India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":90,"ride_trip_id":90,"ride_driver_id":31,"ride_booked_on":"2018-07-24 14:54:27","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"14:54:27","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Kazhakkoottam,kerala,India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":89,"ride_trip_id":89,"ride_driver_id":31,"ride_booked_on":"2018-07-24 14:54:07","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"14:54:07","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Kazhakkoottam,kerala,India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":88,"ride_trip_id":88,"ride_driver_id":31,"ride_booked_on":"2018-07-24 14:53:55","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"14:53:55","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Kazhakkoottam,kerala,India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":87,"ride_trip_id":87,"ride_driver_id":31,"ride_booked_on":"2018-07-24 14:53:01","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"14:53:01","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Kazhakkoottam,kerala,India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":82,"ride_trip_id":82,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:41:07","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:41:07","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":81,"ride_trip_id":81,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:41:02","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:41:02","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":80,"ride_trip_id":80,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:40:15","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:40:15","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":79,"ride_trip_id":79,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:40:11","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:40:11","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":78,"ride_trip_id":78,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:40:02","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:40:02","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":77,"ride_trip_id":77,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:39:57","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:39:57","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":76,"ride_trip_id":76,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:37:17","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:37:17","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":75,"ride_trip_id":75,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:37:13","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:37:13","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":74,"ride_trip_id":74,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:37:08","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:37:08","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":73,"ride_trip_id":73,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:37:04","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:37:04","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":72,"ride_trip_id":72,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:57","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:57","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":71,"ride_trip_id":71,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:53","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:53","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":70,"ride_trip_id":70,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:47","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:47","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":69,"ride_trip_id":69,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:43","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:43","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":68,"ride_trip_id":68,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:39","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:39","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":67,"ride_trip_id":67,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:35","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:35","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":66,"ride_trip_id":66,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:31","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:31","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":65,"ride_trip_id":65,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:15","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:15","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":64,"ride_trip_id":64,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:10","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:10","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":63,"ride_trip_id":63,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:06","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:06","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":62,"ride_trip_id":62,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:36:02","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:36:02","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":61,"ride_trip_id":61,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:35:49","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:35:49","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":60,"ride_trip_id":60,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:35:45","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:35:45","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":59,"ride_trip_id":59,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:35:14","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:35:14","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":58,"ride_trip_id":58,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:35:10","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:35:10","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":57,"ride_trip_id":57,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:35:06","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:35:06","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":56,"ride_trip_id":56,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:35:02","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:35:02","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":55,"ride_trip_id":55,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:34:34","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:34:34","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":54,"ride_trip_id":54,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:34:28","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:34:28","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":53,"ride_trip_id":53,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:34:22","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:34:22","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":52,"ride_trip_id":52,"ride_driver_id":10,"ride_booked_on":"2018-07-24 13:33:54","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:33:54","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":51,"ride_trip_id":51,"ride_driver_id":31,"ride_booked_on":"2018-07-24 13:33:18","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"13:33:18","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Thiruvananthapuram, Kerala, India","ride_to":"kollam bus stop","driver_data":null},{"ride_id":37,"ride_trip_id":37,"ride_driver_id":10,"ride_booked_on":"2018-07-23 14:10:23","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"14:10:23","ride_amount":"250","ride_status":"completed","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":36,"ride_trip_id":36,"ride_driver_id":10,"ride_booked_on":"2018-07-24 01:08:32","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"01:08:32","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":35,"ride_trip_id":35,"ride_driver_id":10,"ride_booked_on":"2018-07-24 01:07:30","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"01:07:30","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":34,"ride_trip_id":34,"ride_driver_id":10,"ride_booked_on":"2018-07-24 01:06:53","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"01:06:53","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":33,"ride_trip_id":33,"ride_driver_id":10,"ride_booked_on":"2018-07-26 05:24:02","ride_booked_on_date":"2018-07-26","ride_booked_on_time":"05:24:02","ride_amount":"250","ride_status":"completed","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":32,"ride_trip_id":32,"ride_driver_id":17,"ride_booked_on":"2018-07-24 00:59:11","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"00:59:11","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":31,"ride_trip_id":31,"ride_driver_id":17,"ride_booked_on":"2018-07-26 04:50:22","ride_booked_on_date":"2018-07-26","ride_booked_on_time":"04:50:22","ride_amount":"250","ride_status":"cancelled","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"Chaka Peta Residencial, Palkulangara, Thiruvananthapuram, Kerala 695024","ride_to":"kollam bus stop","driver_data":null},{"ride_id":15,"ride_trip_id":15,"ride_driver_id":31,"ride_booked_on":"2018-07-23 23:12:39","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"23:12:39","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":11,"ride_trip_id":33,"ride_driver_id":10,"ride_booked_on":"2018-07-26 05:24:05","ride_booked_on_date":"2018-07-26","ride_booked_on_time":"05:24:05","ride_amount":"250","ride_status":"completed","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam, Trivandum","ride_to":"sreekaryam","driver_data":null},{"ride_id":10,"ride_trip_id":10,"ride_driver_id":31,"ride_booked_on":"2018-07-24 02:30:40","ride_booked_on_date":"2018-07-24","ride_booked_on_time":"02:30:40","ride_amount":"250","ride_status":"completed","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":9,"ride_trip_id":9,"ride_driver_id":31,"ride_booked_on":"2018-07-25 21:17:02","ride_booked_on_date":"2018-07-25","ride_booked_on_time":"21:17:02","ride_amount":"250","ride_status":"booked","ride_payment_status":"paid","ride_payment_time":"2018-07-26 09:47:02","ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":8,"ride_trip_id":8,"ride_driver_id":10,"ride_booked_on":"2018-07-23 13:03:23","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"13:03:23","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":7,"ride_trip_id":7,"ride_driver_id":10,"ride_booked_on":"2018-07-23 12:45:54","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"12:45:54","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":6,"ride_trip_id":6,"ride_driver_id":10,"ride_booked_on":"2018-07-23 12:43:57","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"12:43:57","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":5,"ride_trip_id":5,"ride_driver_id":10,"ride_booked_on":"2018-07-23 12:41:48","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"12:41:48","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":4,"ride_trip_id":4,"ride_driver_id":10,"ride_booked_on":"2018-07-23 12:41:44","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"12:41:44","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":3,"ride_trip_id":3,"ride_driver_id":10,"ride_booked_on":"2018-07-23 12:40:22","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"12:40:22","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":2,"ride_trip_id":2,"ride_driver_id":10,"ride_booked_on":"2018-07-23 12:39:17","ride_booked_on_date":"2018-07-23","ride_booked_on_time":"12:39:17","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null},{"ride_id":1,"ride_trip_id":1,"ride_driver_id":9,"ride_booked_on":"2018-07-26 03:14:42","ride_booked_on_date":"2018-07-26","ride_booked_on_time":"03:14:42","ride_amount":"250","ride_status":"completed","ride_payment_status":"unpaid","ride_payment_time":null,"ride_seats":2,"ride_from":"kazhakoottam","ride_to":"sreekaryam","driver_data":null}]
     */

    private boolean status;
    private String message;
    private int count;
    private List<DataEntity> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity  implements Serializable{
        /**
         * ride_id : 119
         * ride_trip_id : 33
         * ride_driver_id : 10
         * ride_booked_on : 2018-07-26 05:52:15
         * ride_booked_on_date : 2018-07-26
         * ride_booked_on_time : 05:52:15
         * ride_amount : 250
         * ride_status : completed
         * ride_payment_status : paid
         * ride_payment_time : 2018-07-26 18:22:15
         * ride_seats : 2
         * ride_from :  kanyakumari
         * ride_to : kovalam
         * driver_data : null
         */

        private int ride_id;
        private int ride_trip_id;
        private int ride_driver_id;
        private String ride_booked_on;

        public String getRide_type() {
            return ride_type;
        }

        public void setRide_type(String ride_type) {
            this.ride_type = ride_type;
        }

        private String ride_type;
        private String ride_booked_on_date;
        private String ride_booked_on_time;
        private String ride_amount;
        private String ride_status;
        private String ride_payment_status;
        private String ride_payment_time;
        private int ride_seats;
        private String ride_from;
        private String ride_to;
        private DriverDataDTO driver_data;

        public int getRide_id() {
            return ride_id;
        }

        public void setRide_id(int ride_id) {
            this.ride_id = ride_id;
        }

        public int getRide_trip_id() {
            return ride_trip_id;
        }

        public void setRide_trip_id(int ride_trip_id) {
            this.ride_trip_id = ride_trip_id;
        }

        public int getRide_driver_id() {
            return ride_driver_id;
        }

        public void setRide_driver_id(int ride_driver_id) {
            this.ride_driver_id = ride_driver_id;
        }

        public String getRide_booked_on() {
            return ride_booked_on;
        }

        public void setRide_booked_on(String ride_booked_on) {
            this.ride_booked_on = ride_booked_on;
        }

        public String getRide_booked_on_date() {
            return ride_booked_on_date;
        }

        public void setRide_booked_on_date(String ride_booked_on_date) {
            this.ride_booked_on_date = ride_booked_on_date;
        }

        public String getRide_booked_on_time() {
            return ride_booked_on_time;
        }

        public void setRide_booked_on_time(String ride_booked_on_time) {
            this.ride_booked_on_time = ride_booked_on_time;
        }

        public String getRide_amount() {
            return ride_amount;
        }

        public void setRide_amount(String ride_amount) {
            this.ride_amount = ride_amount;
        }

        public String getRide_status() {
            return ride_status;
        }

        public void setRide_status(String ride_status) {
            this.ride_status = ride_status;
        }

        public String getRide_payment_status() {
            return ride_payment_status;
        }

        public void setRide_payment_status(String ride_payment_status) {
            this.ride_payment_status = ride_payment_status;
        }

        public String getRide_payment_time() {
            return ride_payment_time;
        }

        public void setRide_payment_time(String ride_payment_time) {
            this.ride_payment_time = ride_payment_time;
        }

        public int getRide_seats() {
            return ride_seats;
        }

        public void setRide_seats(int ride_seats) {
            this.ride_seats = ride_seats;
        }

        public String getRide_from() {
            return ride_from;
        }

        public void setRide_from(String ride_from) {
            this.ride_from = ride_from;
        }

        public String getRide_to() {
            return ride_to;
        }

        public void setRide_to(String ride_to) {
            this.ride_to = ride_to;
        }

        public DriverDataDTO getDriver_data() {
            return driver_data;
        }

        public void setDriver_data(DriverDataDTO driver_data) {
            this.driver_data = driver_data;
        }
    }
}
