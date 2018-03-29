
use File::Find;
use File::Path qw(make_path);

$base = '/Users/remco/workspace/xbeast/apputils';

sub process_file {
	$name = $File::Find::name;
	if ($name =~ /java/) {
	print STDERR "$name\n";
	open(FIN,"$base/$name") or die "Cannot open $base/$name";
	$text = '';
	while ($s = <FIN>) {

		$s =~ s/import beast.core.BEASTInterface/import beast.core.BEASTInterface/;
		$s =~ s/import beast.core.BEASTObject/import beast.core.BEASTObject/;
		$s =~ s/import beast.core.CalculationNode/import beast.core.CalculationNode/;
		$s =~ s/import beast.core.Citation/import beast.core.Citation/;
		$s =~ s/import beast.core.Description/import beast.core.Description/;
		$s =~ s/import beast.core.Function/import beast.core.Function/;
		$s =~ s/import beast.core.Input/import beast.core.Input/;
		$s =~ s/import beast.core.InputForAnnotatedConstructor/import beast.core.InputForAnnotatedConstructor/;
		$s =~ s/import beast.core.Loggable/import beast.core.Loggable/;
		$s =~ s/import beast.core.Param/import beast.core.Param/;
		$s =~ s/import beast.core.parameter.Parameter/import beast.core.parameter.Parameter/;
		$s =~ s/import beast.core.Runnable/import beast.core.Runnable/;
		$s =~ s/import beast.core.State/import beast.core.State/;
		$s =~ s/import beast.core.StateNode/import beast.core.StateNode/;
		$s =~ s/import beast.core.StateNodeInitialiser/import beast.core.StateNodeInitialiser/;
		$s =~ s/import beast.core.util.Log/import beast.core.util.Log/;

		$s =~ s/import org.json.JSON/import org.json.JSON/;
		$s =~ s/import org.json.JSONArray/import org.json.JSONArray/;
		$s =~ s/import org.json.JSONException/import org.json.JSONException/;
		$s =~ s/import org.json.JSONObject/import org.json.JSONObject/;
		$s =~ s/import org.json.JSONStringer/import org.json.JSONStringer/;
		$s =~ s/import org.json.JSONTokener/import org.json.JSONTokener/;

		$s =~ s/import beast.app.BeastDialog/import beast.app.BeastDialog/;
		$s =~ s/import beast.app.BEASTVersion/import beast.app.BEASTVersion/;
		$s =~ s/import beast.app.BEASTVersion2/import beast.app.BEASTVersion2/;

		$s =~ s/import beast.app.util.Arguments/import beast.app.util.Arguments/;
		$s =~ s/import beast.app.util.ErrorLogHandler/import beast.app.util.ErrorLogHandler/;
		$s =~ s/import beast.app.util.MessageLogHandler/import beast.app.util.MessageLogHandler/;
		$s =~ s/import beast.app.util.Utils/import beast.app.util.Utils/;
		$s =~ s/import beast.app.util.Utils6/import beast.app.util.Utils6/;
		$s =~ s/import beast.app.util.Version/import beast.app.util.Version/;
		$s =~ s/import beast.app.util.WholeNumberField/import beast.app.util.WholeNumberField/;
		$s =~ s/import beast.util.InputType/import beast.util.InputType/;
		$s =~ s/import beast.util.JSONParser/import beast.util.JSONParser/;
		$s =~ s/import beast.util.JSONParserException/import beast.util.JSONParserException/;
		$s =~ s/import beast.util.JSONProducer/import beast.util.JSONProducer/;
		$s =~ s/import beast.util.Package/import beast.util.Package/;
		$s =~ s/import beast.util.PackageDependency/import beast.util.PackageDependency/;
		$s =~ s/import beast.util.PackageManager/import beast.util.PackageManager/;
		$s =~ s/import beast.util.PackageVersion/import beast.util.PackageVersion/;
		$s =~ s/import beast.util.XMLParser/import beast.util.XMLParser/;
		$s =~ s/import beast.util.XMLParserException/import beast.util.XMLParserException/;
		$s =~ s/import beast.util.XMLParserUtils/import beast.util.XMLParserUtils/;
		$s =~ s/import beast.util.XMLProducer/import beast.util.XMLProducer/;
			
		$s =~ s/import beast.util.AddOnManager/import beast.util.PackageManager/;


		$s =~ s/AddOnManager/PackageManager/;
		$s =~ s/getCitations\(\)/getCitationString()/;
		$s =~ s/\bgetID\(\)/getId()/g;
		$s =~ s/\bsetID\(/setId(/;
		$s =~ s/Class.forName\(/xbeast.util.PackageManager.forName(/;

		$text .= $s;
	}
	close FIN;

	open(FOUT,">$base/$name") or die "Cannot write $name";
	print FOUT $text;
	close FOUT;
	}
}

find(\&process_file, ('.'));	
