/*
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package es.uah.cc.ie.utils;

// base64
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
// GSON
import com.google.gson.annotations.SerializedName;

// import es.uah.cc.ie.portalupdater.*;
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.dc.translator.DublinCore;

/**
// Maven repositories. Base64, GSON
<dependency>
  <groupId>commons-codec</groupId>
  <artifactId>commons-codec</artifactId>
  <version>1.8</version>
</dependency>
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.2.4</version>
</dependency>
**/

/**
 * @version 0.x
 * @author: Julian 
 */
public class Test {

  @SerializedName("w")
  private int width;
    
  @SerializedName("h")
  private int height;
        
  @SerializedName("d")
  private int depth;

  public void Test() {
    this.width = 10;
    this.height = 10;
    this.depth = 10;
  }
}
