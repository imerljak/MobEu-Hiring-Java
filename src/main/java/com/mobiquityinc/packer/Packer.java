package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Packer {

    private static final PackingStrategy PACKING_STRATEGY = new KnapsackPackingStrategy();

    private Packer() {
    }

    /**
     * Receives a formatted .txt file absolute path containing information about packages and the 'things' it can
     * contain.
     *
     * @param filePath absolute path of the .txt file containing the data.
     * @return a string containing every thing that was added to the corresponding package line
     * @throws APIException when a constraint is violated.
     */
    public static String pack(String filePath) throws APIException {
        final List<Package> packages = new ArrayList<>();

        try (final FileInputStream fs = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fs, "UTF-8")) {

            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();

                final String[] splitLine = line.split("\\s:\\s");

                final Package pack = PackageBuilder.build(splitLine[0]);

                final List<Thing> things = ThingBuilder.build(splitLine[1].split("\\s"));

                PACKING_STRATEGY.pack(pack, things);

                packages.add(pack);
            }

        } catch (IOException e) {
            throw new APIException(e.getMessage(), e);
        }

        // Returns a '\n' separated string with every package's thing index separated by a comma
        // e.g: 1,2,3\n3\n-\n6,8
        return packages.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

}
