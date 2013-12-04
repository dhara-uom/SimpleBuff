package test;

import net.opengis.wps.x100.CapabilitiesDocument;
import net.opengis.wps.x100.ProcessDescriptionType;
import org.dhara.wps.filter.Filter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.n52.wps.client.WPSClientException;
import pckg.Main;
import pckg.Params;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: newair
 * Date: 11/10/13
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JUnit4.class)
public class TestProcess {

  private static Main main;
    private CapabilitiesDocument capabilitiesDocument;
    private ProcessDescriptionType describeProcessDocument;

    @BeforeClass
    public static void setUp(){

        main = new Main();

    }

    @Test

    public void capabilitiesDocExist(){
        try {
             capabilitiesDocument = main.requestGetCapabilities(Params.WPSURL);
            Assert.assertNotNull("Capabilities Document Not Null",capabilitiesDocument);

        } catch (WPSClientException e) {
              Assert.fail("WPSClientException thrown");
        }
    }

    @Test
        public void describeProcessExist(){
            try {
                 describeProcessDocument = main.requestDescribeProcess(
                        Params.WPSURL, Params.processID);
                Assert.assertNotNull("Capabilities Document Not Null",describeProcessDocument);

            } catch (IOException e ) {
                Assert.fail("IO exception thrown at describe process ");
            }
    }


    @Test
    public void testExecute(){
        try {

            describeProcessDocument = main.requestDescribeProcess(
                    Params.WPSURL, Params.processID);

            System.out.println(describeProcessDocument);
            HashMap<String, Object> inputs = new HashMap<String, Object>();
            // complex data by reference
            inputs.put(
                    "wps_host_url_sb", Params.WPSURL);

            // literal data
            inputs.put("wfs_host_url_sb", Params.WFS_HOST_URL);
            inputs.put("wfs_id", Params.WFS_ID);
            inputs.put("width", Params.width);
            inputs.put("name1", Params.name1);
            inputs.put("name2", Params.name2);

            String output= main.executeProcess(Params.WPSURL, Params.processID,
                    describeProcessDocument, inputs);
            Assert.assertNotNull("Output Not Null",output);
            System.out.println(Filter.getResourceId(output));

        } catch (IOException e ) {
           // Assert.fail("IO exception thrown at Execute process ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGeoserver() throws IOException {
        String USER_AGENT = "Mozilla/5.0";
/*
        HttpClient httpClient = new HttpClient("http://localhost:8094/geoserver/N52/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=N52:Shape_2bf61b66-bb9d-4cde-b6a4-f896bf922ae35750486012744717869&maxFeatures=50&outputFormat=GML2",1);
*/
        String url =   "http://localhost:8094/geoserver/Dhara/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Dhara:LS_Bulathsinhala&maxFeatures=50&outputFormat=GML2";
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
       Assert.assertNotNull("GeoServer response arriving",response.toString());

    }



    @Test
    public void testFilter(){

        String id=Filter.getResourceId(DummyResponse.response);

        Assert.assertEquals("N52:Shape_d7081c5b-c07a-4adb-9003-47592e4346d78175463493701042351",id);

    }

    @Test
    public void testOutputSaved(){
/*
        try {

            describeProcessDocument = main.requestDescribeProcess(
                    Params.WPSURL, Params.processID);
            HashMap<String, Object> inputs = new HashMap<String, Object>();
            // complex data by reference
            inputs.put(
                    "data", Params.WFSURL);

            // literal data
            inputs.put("width", Params.width);
            String output= main.executeProcess(Params.WPSURL, Params.processID,
                    describeProcessDocument, inputs);
            Assert.assertNotNull("Output Not Null",output);
            System.out.println(Filter.getResourceId(output));




        } catch (IOException e ) {
            // Assert.fail("IO exception thrown at Execute process ");
        } catch (Exception e) {
            e.printStackTrace();
        }             */
    }









}
