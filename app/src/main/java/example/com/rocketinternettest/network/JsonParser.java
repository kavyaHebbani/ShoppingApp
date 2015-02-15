package example.com.rocketinternettest.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import example.com.rocketinternettest.Stream;

/**
 * Created by Kavya Shree, 13/02/15.
 */
public class JsonParser {
    private ObjectMapper mapper = new ObjectMapper();

    public JsonParser() {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    public List<Stream> parseStreamList(InputStream responseBody)
            throws IOException, JSONException {
        List<Stream> streamList = new ArrayList<>();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        JsonNode node = mapper.readTree(responseBody);
        node = node.get("metadata");
        final JsonNode resultsNode = node.get("results");
        Iterator iterator = resultsNode.elements();
        while (iterator.hasNext()) {
            JsonNode temp = (JsonNode) iterator.next();
            JsonNode dataNode = temp.get("data");
            JsonNode imagesNode = temp.get("images");
            Iterator iterator2 = imagesNode.elements();
            JsonNode pathTempNode = (JsonNode) iterator2.next();
            JsonNode pathNode = pathTempNode.get("path");
            Stream stream = new Stream();

            stream.setName(dataNode.get("name").asText());
            stream.setBrand(dataNode.get("brand").asText());
            stream.setPrice(dataNode.get("price").asText());
            stream.setImageUrl(pathNode.asText());

            streamList.add(stream);
        }

        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        return streamList;
    }
}