package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Models.Location;
import com.GPSTracker.gpstracker.Repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenStreetMapService {

    @Autowired
    LocationRepository locationRepository;

    public String generateOMSUrl(Integer uid) {
        String[] lonAndLat = locationRepository.getLonAndLatFromConnection(uid).split(",");
        return String.format("<iframe width=\"900\" height=\"700\" src=\"https://www.openstreetmap.org/export/embed.html?bbox=%s%%2C%s%%2C%s%%2C%s&amp;layer=mapnik&amp;marker=%s%%2C%s\" style=\"border: 1px solid black\"></iframe><br/><small><a href=\"https://www.openstreetmap.org/?mlat=%s&amp;mlon=%s#map=17/%s/%s\">Wyświetl większą mapę</a></small>",
                lonAndLat[0], lonAndLat[1], lonAndLat[0], lonAndLat[1], lonAndLat[1], lonAndLat[0],
                lonAndLat[1], lonAndLat[0], lonAndLat[1], lonAndLat[0]);
    }


    public String generatePathMap(Integer uid) {
        List<Location> locations = locationRepository.getListOfLonAndLatFromConnection(uid);

        if (locations.isEmpty()) {
            return "No data available.";
        }
        StringBuilder mapHtml = new StringBuilder();
        mapHtml.append("<!DOCTYPE html>");
        mapHtml.append("<html>");
        mapHtml.append("<head>");
        mapHtml.append("<title>Map with Path</title>");
        mapHtml.append("<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />");
        mapHtml.append("<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>");
        mapHtml.append("</head>");

        mapHtml.append("<body>");
        mapHtml.append("<div id=\"map\" style=\"height: 97vh;\"></div>");

        mapHtml.append("<script>");
        mapHtml.append("var savedSettings = JSON.parse(localStorage.getItem('mapSettings'));");
        mapHtml.append("var defaultLat = ").append(locations.get(0).getLat()).append(";");
        mapHtml.append("var defaultLon = ").append(locations.get(0).getLon()).append(";");
        mapHtml.append("var defaultZoom = 10;");
        mapHtml.append("if (savedSettings) {");
        mapHtml.append("  defaultLat = savedSettings.lat;");
        mapHtml.append("  defaultLon = savedSettings.lon;");
        mapHtml.append("  defaultZoom = savedSettings.zoom;");
        mapHtml.append("}");
        mapHtml.append("var map = L.map('map').setView([defaultLat, defaultLon], defaultZoom);");
        mapHtml.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: 'Map data Â© <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors' }).addTo(map);");
        mapHtml.append("var pathPoints = [");
        for (Location location : locations) {
            mapHtml.append("[").append(location.getLat()).append(", ").append(location.getLon()).append("],");
        }
        mapHtml.deleteCharAt(mapHtml.length() - 1);
        mapHtml.append("];");
        mapHtml.append("L.polyline(pathPoints, {color: 'blue'}).addTo(map);");
         for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            mapHtml.append("L.marker([").append(location.getLat()).append(", ").append(location.getLon()).append("])");
            mapHtml.append(".bindPopup('Latitude: ").append(location.getLat()).append("<br>Longitude: ").append(location.getLon()).append("<br>Time: ").append(location.getDateAndTime()).append("')");
            mapHtml.append(".addTo(map);");
        }
        mapHtml.append("</script>");

        mapHtml.append("<script>");
        mapHtml.append("function refreshPage() {");
        mapHtml.append("  setTimeout(function(){");
        mapHtml.append("    location.reload();");
        mapHtml.append("  }, 30000);");
        mapHtml.append("}");
        mapHtml.append("refreshPage();");
        mapHtml.append("</script>");

        mapHtml.append("</body>");
        mapHtml.append("</html>");
        return mapHtml.toString();
    }
}
