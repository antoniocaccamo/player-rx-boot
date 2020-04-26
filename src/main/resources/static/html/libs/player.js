
$.support.cors = true;

var coords = ''


function setLang(lang) {
	Player.weather.lang = lang;
}

function setCoords(slat, slng) {
	Player.weather.setCoordinates(slat, slng);
	Player.weather.wwo.refresh();
}

function getCoords() {
	coords = Player.weather.latlngToQuery();
}

Player = {} || Player;

Player.pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div></div></div>');





Player.showPleaseWait = function () {
	Player.pleaseWaitDiv.modal();
};


Player.hidePleaseWait = function () {
	Player.pleaseWaitDiv.modal('hide');
};


/**
 * weather functions
 */

Player.weather = {} || Player.weather;

//Player.addNameSpace("weather");

Player.weather.apikey = "AIzaSyD9ROF-twnrhiOLUhXzoSRH-N0EdoEYPYo";

Player.weather.template = 0;
Player.weather.map;
Player.weather.marker;

Player.weather.lang;

Player.weather.address

Player.weather.labels = {};
Player.weather.labels['en'] = {
	latitude: 'latitude', longitude: 'longitude',
	address: 'address', address_placeholder: 'search address..',
	home: 'home', preview: 'preview',
	days: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
};
Player.weather.labels['it'] = {
	latitude: 'latitudine', longitude: 'longitudine',
	address: 'indirizzo', address_placeholder: 'ricerca indirizzo..',
	home: 'home', preview: 'anteprima',
	days: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab']
};
Player.weather.labels['es'] = {
	latitude: 'latitude', longitude: 'longitude',
	address: 'address', address_placeholder: 'search address..',
	home: 'home', preview: 'preview',
	days: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
};


Player.weather.setLang = function (lang) {
	Player.weather.lang = lang;
}

Player.weather.setCoordinates = function (slan, slng) {
	Player.weather.latlng = L.latLng([slan, slng]);

}


Player.weather.geocode = function () {

	var query = {
		lat: Player.weather.latlng.lat.toFixed(2),
		lon: Player.weather.latlng.lng.toFixed(2),
		addressdetails: 0,
		zoom: 12,
		format: 'json'
	};

	$.getJSON("https://nominatim.openstreetmap.org/reverse", query)
		.done(function (jsonres) {
			//          console.log(jsonres.display_name);
			Player.weather.wwo.write(jsonres.display_name);
		})
		.fail(function (jqxhr, textStatus, error) {

			var err = textStatus + ", " + error;

			console.log("Request Failed: " + err);

		});


}


/*
Player.weather.geocodeAddress = function (address) {
	Player.showPleaseWait();
	Player.weather.geocoder.geocode( {'address': address}, function(results, status){
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[0]) {							            
				address = results[0].formatted_address;
				$('#address').val(address);
				Player.weather.latlng = results[0].geometry.location;
				$('#lat').text(Player.weather.latlng.lat().toFixed(2));
				$('#lng').text(Player.weather.latlng.lng().toFixed(2));
				Player.weather.map.setZoom(11);	
				Player.weather.map.setCenter(Player.weather.latlng);
				if ( Player.weather.marker == null) {
					Player.weather.marker = new google.maps.Marker({
						position: Player.weather.map.getCenter(),
						map: Player.weather.map
					});
				} else {
					Player.weather.marker.setPosition(Player.weather.map.getCenter());
				}
				theJavaFunction(Player.weather.latlngToQuery());
				 
			} else {
				//alert('No results found');
			}
		} else {
			//alert('Geocoder failed due to: ' + status);
		}
		Player.hidePleaseWait();
	});
}
*/

Player.weather.latlngToQuery = function () {
	return Player.weather.latlng.lat.toFixed(2) + ',' + Player.weather.latlng.lng.toFixed(2);
};


/**
 * WWO weather functions
   */


Player.weather.wwo = {} || Player.weather.wwo;

Player.weather.wwo.key = '75344351ed344d7f9fc120156162903';
Player.weather.wwo.baseurl = 'http://api.worldweatheronline.com/premium/v1/weather.ashx';
Player.weather.wwo.teplateCurrentCondiction = [_.template(
	'<tr>                                                                                               \
			<td class="widthb"><img id="logo" class="logo" src="images/artlogo.png"> </td>                                                         \
			<td class="width33">                                                                            \
			<img class="img-rounded img-responsive img-today center-block topimg"                                       \
			src="<%= image_url%>"                                                                   \
			alt="Weather Condition">                                                                \
			</td>                                                                                           \
			<td class="width33">                                                                         \
			<div class="btn-group">									                                    \
			<button type="button" class="btn1 btn-default btn-lg active"><%= tmp %>&deg;</button>    \
			</div>                                                                                      \
			</td>                                                                                           \
	</tr>'),
_.template('<div class="row></div><div class="row>"')
];

Player.weather.wwo.teplateForecastCondiction = [
	_.template(
		'<td class="width33"> \
	    			<h4><%= forecastDate%></h4> \
	    			<img class="img-rounded img-forecast img-responsive center-block"  src="<%= image_url%>" alt="Weather Condition">                                                                \
	    		<div class="btn-group" data-toggle="buttons"> \
	    		  <label class="btn btn-primary" style="width: 50%;">\
	    		    <input type="radio" name="options" id="option1" autocomplete="off" checked=""><%= tmp_min%>&deg;</label> \
	    		  <label class="btn btn-danger" style="width: 50%;"> \
	    		    <input type="radio" name="options" id="option2" autocomplete="off"><%= tmp_max%>&deg;</label> \
	    		</div>                                                                                     \
	    		</td>'),
	_.template('<div class="col-xs-4 col-md-4 text-center vmiddle">                                     \
        				<p class=""><%= forecastDate%></td> </p><p class="vmiddle">  \
                        	<img    src="<%= image_url%>" >                                                       \
                            <br/>                                                                                  \
        					<a role="button" class="btn btn-danger"   ><%= tmp_max %>&deg;</a> <a role="button" class="btn btn-primary"  ><%= tmp_min %>&deg;</a>            \
                            <br/>                                                                                   \
                            <%= descr%>                                                                            \
                        </p> \
        		</div>')
];

Player.weather.wwo.refresh = function () {
	Player.showPleaseWait();
	Player.weather.geocode();
	Player.hidePleaseWait();

};

Player.weather.wwo.regeocode = function () {
	/*
	Player.weather.geocoder.geocode({'latLng': Player.weather.latlng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[1]) {					
				Player.weather.wwo.write( results[1].formatted_address);
			} else {
				Player.hidePleaseWait();	
				//alert('No results found');
				
			}
		} else {
			Player.hidePleaseWait();	
			//alert('Geocoder failed due to: ' + status);
		}			
	});
	*/
	Player.weather.wwo.write(Player.weather.latlngToQuery());
}

Player.weather.wwo.write = function (address) {

	var query = {
		q: Player.weather.latlngToQuery(),
		num_of_days: '3',
		includelocation: 'yes',
		lang: Player.weather.lang,
		format: 'json',
		tp: 24,
		key: Player.weather.wwo.key
	};

	$.getJSON(Player.weather.wwo.baseurl + '?callback=?', query)
		.done(function (jsonres) {

			$('#address').text(address);

			if (!jsonres.data.hasOwnProperty('error')) {
				var html = '';
				switch (Player.weather.template) {

					case 1:
						for (var i = 0; i < 3; i++) {
							var dd = new Date(jsonres.data.weather[i].date);
							var vars = {
								forecastDate: Player.weather.labels[Player.weather.lang].days[dd.getDay()] + ' ' + dd.getDate(),
								image_url: "images/" + jsonres.data.weather[i].hourly[0].weatherIconUrl[0].value.split('/').pop(),
								tmp_min: jsonres.data.weather[i].mintempC,
								tmp_max: jsonres.data.weather[i].maxtempC,
								descr: (jsonres.data.weather[i].hourly[0].hasOwnProperty('lang_' + Player.weather.lang) ?
									jsonres.data.weather[i].hourly[0]["lang_" + Player.weather.lang][0].value :
									jsonres.data.weather[i].hourly[0].weatherDesc[0].value
								)
							};
							html += Player.weather.wwo.teplateForecastCondiction[Player.weather.template](vars);
						}
						$('#weather_forecast').html(html);

						break;

					case 0:
					default:

						html += '<tbody>';
						// current condition
						var vars = {
							location: address,
							image_url: "images/" + jsonres.data.current_condition[0].weatherIconUrl[0].value.split('/').pop(),
							tmp: jsonres.data.current_condition[0].temp_C
						};
						html += Player.weather.wwo.teplateCurrentCondiction[Player.weather.template](vars);
						// forecast condition
						for (var i = 0; i < 3; i++) {
							var dd = new Date(jsonres.data.weather[i].date);
							var vars = {
								forecastDate: Player.weather.labels[Player.weather.lang].days[dd.getDay()] + ' ' + dd.getDate(),
								image_url: "images/" + jsonres.data.weather[i].hourly[0].weatherIconUrl[0].value.split('/').pop(),
								tmp_min: jsonres.data.weather[i].mintempC,
								tmp_max: jsonres.data.weather[i].maxtempC
							};
							html += Player.weather.wwo.teplateForecastCondiction[Player.weather.template](vars);
						}
						html += '</tbody>';
						/*if ( console !== null ) {
								console.log(html);
							}*/
						$('#weather_table').html(html);
				}
			}

			var lu = "updated @ " + new Date();
			$("#logo").attr("title", lu);

			Player.hidePleaseWait();
		});
}

Player.weather.wwo.updateDataWeahter = function (jstring) {

	Player.showPleaseWait();

	try {

		var jsonres = JSON.parse(jstring);


		if (!jsonres.data.hasOwnProperty('error')) {
			var html = '';
			switch (Player.weather.template) {

				case 1:
					for (var i = 0; i < 3; i++) {
						var dd = new Date(jsonres.data.weather[i].date);
						var vars = {
							forecastDate: Player.weather.labels[Player.weather.lang].days[dd.getDay()] + ' ' + dd.getDate(),
							image_url: "images/" + jsonres.data.weather[i].hourly[0].weatherIconUrl[0].value.split('/').pop(),
							tmp_min: jsonres.data.weather[i].mintempC,
							tmp_max: jsonres.data.weather[i].maxtempC,
							descr: (jsonres.data.weather[i].hourly[0].hasOwnProperty('lang_' + Player.weather.lang) ?
								jsonres.data.weather[i].hourly[0]["lang_" + Player.weather.lang][0].value :
								jsonres.data.weather[i].hourly[0].weatherDesc[0].value
							)
						};
						html += Player.weather.wwo.teplateForecastCondiction[Player.weather.template](vars);
					}
					$('#weather_forecast').html(html);

					break;

				case 0:
				default:

					html += '<tbody>';
					// current condition
					var vars = {
						//	location  : address,
						image_url: "images/" + jsonres.data.current_condition[0].weatherIconUrl[0].value.split('/').pop(),
						tmp: jsonres.data.current_condition[0].temp_C
					};
					html += Player.weather.wwo.teplateCurrentCondiction[Player.weather.template](vars);
					// forecast condition
					for (var i = 0; i < 3; i++) {
						var dd = new Date(jsonres.data.weather[i].date);
						var vars = {
							forecastDate: Player.weather.labels[Player.weather.lang].days[dd.getDay()] + ' ' + dd.getDate(),
							image_url: "images/" + jsonres.data.weather[i].hourly[0].weatherIconUrl[0].value.split('/').pop(),
							tmp_min: jsonres.data.weather[i].mintempC,
							tmp_max: jsonres.data.weather[i].maxtempC
						};
						html += Player.weather.wwo.teplateForecastCondiction[Player.weather.template](vars);
					}
					html += '</tbody>';
					/*if ( console !== null ) {
	                        console.log(html);
	                    }*/
					$('#weather_table').html(html);
			}
		}
		var lu = "updated @ " + new Date();
		$("#logo").attr("title", lu);
		WeatherUpdaterDone(lu);
	} catch (err) {
		WeatherUpdaterDone("ERROR on weather update @ " + new Date());
	}

	Player.hidePleaseWait();

}


/** 
 * video player functions
 */

Player.video = {} || Player.video;

Player.video.createMediaElementPlayer = function (me) {
	var mep = new MediaElementPlayer(me, {
		stretching: 'fill',
		muted : true,
		clickToPlayPause : false,
		hideVideoControlsOnLoad : true,
		hideVideoControlsOnPause : true,
		features: ['playpause', 'progress', 'current', 'duration', 'volume', 'fullscreen'],
		success: function (media, domObject) {			

			console.log("success : " + media);
		},
		error: function (mediaElement, domObject) {
			console.log("error : " + mediaElement);
		}
	});

	return mep;
}

Player.video.load = function ( mep , mediaURL) {

	mep.setSrc(mediaURL);
	mep.load();	
}



