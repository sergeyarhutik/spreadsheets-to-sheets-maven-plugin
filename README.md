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
                <artifactId>spreadsheets-to-xlsx-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <phase>compile</phase>
                        <goals>
                            <goal>
                                getsheets
                            </goal>
                        </goals>
                        <configuration>
                            <spreadsheet>
                                <spreadsheet>
                                    <link>https://docs.google.com/spreadsheets/d/19Ps-e1VT12Ciymn6-Ih6pwYpRp9IwTI1JVYqKhBYzro/export?format=xlsx</link>
                                    <path>target/somedir1/</path>
                                </spreadsheet>
                                   <spreadsheet>
                                    <link>https://docs.google.com/spreadsheets/d/19Ps-e1VT12Ciymn6-Ih6pwYpRp9IwTI1JVYqKhBYzro/export?format=xlsx</link>
                                    <path>target/somedir2/</path>
                                </spreadsheet>
                            </spreadsheet>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```