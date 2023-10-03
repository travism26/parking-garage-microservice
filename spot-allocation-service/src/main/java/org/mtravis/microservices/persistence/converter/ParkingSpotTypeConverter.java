package org.mtravis.microservices.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mtravis.microservices.persistence.ParkingSpotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter(autoApply = true)
public class ParkingSpotTypeConverter implements AttributeConverter<ParkingSpotType, String> {
    public static final Logger LOGGER = LoggerFactory.getLogger(ParkingSpotTypeConverter.class);


    @Override
    public String convertToDatabaseColumn(ParkingSpotType attribute) {
        // SMALL, MEDIUM, LARGE, XLARGE;
        switch (attribute) {
            case SMALL:
                LOGGER.info("Converting {} to string representation", attribute.name());
                return "SMALL";
            case MEDIUM:
                LOGGER.info("Converting {} to string representation", attribute.name());
                return "MEDIUM";
            case LARGE:
                LOGGER.info("Converting {} to string representation", attribute.name());
                return "LARGE";
            case XLARGE:
                LOGGER.info("Converting {} to string representation", attribute.name());
                return "XLARGE";
            default:
                throw new IllegalArgumentException("Parking Type ["+ attribute +"] not supported.");
        }
    }

    @Override
    public ParkingSpotType convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "SMALL":
                LOGGER.info("Converting String representation {} back to ENUM", dbData);
                return ParkingSpotType.SMALL;
            case "MEDIUM":
                LOGGER.info("Converting String representation {} back to ENUM", dbData);
                return ParkingSpotType.MEDIUM;
            case "LARGE":
                LOGGER.info("Converting String representation {} back to ENUM", dbData);
                return ParkingSpotType.LARGE;
            case "XLARGE":
                LOGGER.info("Converting String representation {} back to ENUM", dbData);
                return ParkingSpotType.XLARGE;
            default:
                throw new IllegalArgumentException("ParkingSpotType ["+dbData+"] Not supported.");
        }
    }
}
