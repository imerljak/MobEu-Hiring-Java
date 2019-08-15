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
                addThingsTo(pack, splitLine[1]);

                packages.add(pack);
            }

        } catch (IOException e) {
            throw new APIException(e.getMessage(), e);
        }

        // Returns a '\n' separated string with every package's thing index separated by a comma
        // e.g: 1,2,3\n3\n-\n6,8
        return packages.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Adds every possible 'Thing' into its package.
     *
     * This method is responsible for adding each extracted 'Thing' for the file into its package
     * in the correct order so that the 'Package' has the best possible set of 'Things' in it.
     *
     * The method used to determine the best possible combination is purely based on ordering of the 'Things' before
     * adding them inside de package based on their price in descending order and then, by its weight in ascending order,
     * that way, it is guaranteed to have the 'Things' with the highest price and lowest weight first.
     *
     * In such a way it can be viewed as a 'brute force' method in a way, but it surely is better than trying to create
     * possible combination and check the better one afterwards.
     *
     *
     * @param pack the Package
     * @param stringThing the Things in string format.
     * @throws APIException when an error has occurred :(
     */
    private static void addThingsTo(Package pack, String stringThing) throws APIException {
        ThingBuilder.build(stringThing.split("\\s"))
                .stream()
                .sorted(Comparator.comparing(Thing::getPrice).reversed().thenComparing(Thing::getWeight))
                .forEach(pack::putThing);
    }
}
