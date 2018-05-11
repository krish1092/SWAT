function displayFullMorphologyNames(morphology){
	
	switch(morphology)
	{
		case "IC":
			morphology = "Isolated Cells";
			break;
		
		case "CC":
			morphology = "Cluster of Cells";
			break;
		
		case "BL":
			morphology = "Broken Lines";
			break;
		
		case "BE":
			morphology = "Bow Echoes";
			break;
		
		case "TS":
			morphology = "Squall Lines with Trailing Stratitform";
			break;
			
		case "PS":
			morphology = "Squall Lines with Parallel Stratitform";
			break;
		
		case "LS":
			morphology = "Squall Lines with Leading Stratitform";
			break;
		
		case "NS":
			morphology = "Squall Lines with No Stratitform";
			break;
		
		case "NL":
			morphology = "Non-Linear";
			break;
		
		case "MC":
			morphology = "Mixed Complex";
			break;
	}
	
	return morphology;
}