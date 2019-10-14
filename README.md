# spreadsheets-to-xlsx-maven-plugin
Maven Plugin to download google sheets and convert each sheet into a separate xlsx-file

> Spreadsheet should be available for download by the link

Example of usage:

- pom.xml

```xml
<build>
    <plugins>
         <plugin>
            <groupId>com.epam</groupId>
            <artifactId>google-sheets-to-xlsx-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>getsheets</goal>
                    </goals>
                    <configuration>
                        <fileProperties>
                            <fileProperty>
                                <link>https://docs.google.com/spreadsheets/d/1imI9QfF25kZRTlGBPSddsFmmi9QPztXhSLAsH3YmlJU</link>
                                <path>target/132</path>
                            </fileProperty>
                            <fileProperty>
                                <link>https://docs.google.com/spreadsheets/d/19Ps-e1VT12Ciymn6-Ih6pwYpRp9IwTI1JVYqKhBYzro</link>
                                <path>target/213213</path>
                            </fileProperty>
                        </fileProperties>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```