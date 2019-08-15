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

    public static String pack(String filePath) throws APIException {
        final List<Package> packages = new ArrayList<>();

        try (final FileInputStream fs = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fs, "UTF-8")) {

            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();

                final String[] splittedLine = line.split("\\s:\\s");

                final Package pack = PackageBuilder.build(splittedLine[0]);
                addThingsTo(pack, splittedLine[1]);

                packages.add(pack);
            }

        } catch (IOException e) {
            throw new APIException(e.getMessage(), e);
        }

        return packages.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    private static void addThingsTo(Package pack, String stringifiedThings) throws APIException {
        ThingBuilder.build(stringifiedThings.split("\\s"))
                .stream()
                .sorted(Comparator.comparing(Thing::getPrice).reversed().thenComparing(Thing::getWeight))
                .forEach(pack::putThing);
    }
}
