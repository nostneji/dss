package eu.europa.esig.dss.asic.signature.asice;

import java.util.List;

import eu.europa.esig.dss.DSSDocument;
import eu.europa.esig.dss.asic.ASiCParameters;
import eu.europa.esig.dss.asic.ASiCUtils;
import eu.europa.esig.dss.utils.Utils;

public abstract class AbstractDataToSignASiCEWithXAdES {

	private static final String META_INF = "META-INF/";
	private static final String ZIP_ENTRY_ASICE_METAINF_XADES_SIGNATURE = META_INF + "signatures001.xml";

	protected DSSDocument getASiCManifest(List<DSSDocument> documents) {
		ASiCEWithXAdESManifestBuilder manifestBuilder = new ASiCEWithXAdESManifestBuilder(documents);
		return ASiCUtils.createDssDocumentFromDomDocument(manifestBuilder.build(), META_INF + "manifest.xml");
	}

	protected String getSignatureFileName(final ASiCParameters asicParameters, List<DSSDocument> existingSignatures) {
		if (Utils.isStringNotBlank(asicParameters.getSignatureFileName())) {
			return META_INF + asicParameters.getSignatureFileName();
		}
		if (Utils.isCollectionNotEmpty(existingSignatures)) {
			return ZIP_ENTRY_ASICE_METAINF_XADES_SIGNATURE.replace("001", getSignatureNumber(existingSignatures));
		} else {
			return ZIP_ENTRY_ASICE_METAINF_XADES_SIGNATURE;
		}
	}

	private String getSignatureNumber(List<DSSDocument> existingSignatures) {
		int signatureNumbre = existingSignatures.size() + 1;
		String sigNumberStr = String.valueOf(signatureNumbre);
		String zeroPad = "000";
		return zeroPad.substring(sigNumberStr.length()) + sigNumberStr; // 2 -> 002
	}

}
