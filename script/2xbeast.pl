
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

		$s =~ s/import beast.core.BEASTInterface/import xbeast.core.BEASTInterface/;
		$s =~ s/import beast.core.BEASTObject/import xbeast.core.BEASTObject/;
		$s =~ s/import beast.core.CalculationNode/import xbeast.core.CalculationNode/;
		$s =~ s/import beast.core.Citation/import xbeast.core.Citation/;
		$s =~ s/import beast.core.Description/import xbeast.core.Description/;
		$s =~ s/import beast.core.Function/import xbeast.core.Function/;
		$s =~ s/import beast.core.Input/import xbeast.core.Input/;
		$s =~ s/import beast.core.InputForAnnotatedConstructor/import xbeast.core.InputForAnnotatedConstructor/;
		$s =~ s/import beast.core.Loggable/import xbeast.core.Loggable/;
		$s =~ s/import beast.core.Param/import xbeast.core.Param/;
		$s =~ s/import beast.core.parameter.Parameter/import xbeast.core.parameter.Parameter/;
		$s =~ s/import beast.core.Runnable/import xbeast.core.Runnable/;
		$s =~ s/import beast.core.State/import xbeast.core.State/;
		$s =~ s/import beast.core.StateNode/import xbeast.core.StateNode/;
		$s =~ s/import beast.core.StateNodeInitialiser/import xbeast.core.StateNodeInitialiser/;
		$s =~ s/import beast.core.util.Log/import xbeast.core.util.Log/;

		$s =~ s/import org.json.JSON/import org.json.JSON/;
		$s =~ s/import org.json.JSONArray/import org.json.JSONArray/;
		$s =~ s/import org.json.JSONException/import org.json.JSONException/;
		$s =~ s/import org.json.JSONObject/import org.json.JSONObject/;
		$s =~ s/import org.json.JSONStringer/import org.json.JSONStringer/;
		$s =~ s/import org.json.JSONTokener/import org.json.JSONTokener/;

		$s =~ s/import beast.app.BeastDialog/import xbeast.app.BeastDialog/;
		$s =~ s/import beast.app.BEASTVersion/import xbeast.app.BEASTVersion/;
		$s =~ s/import beast.app.BEASTVersion2/import xbeast.app.BEASTVersion2/;

		$s =~ s/import beast.app.util.Arguments/import xbeast.app.util.Arguments/;
		$s =~ s/import beast.app.util.ErrorLogHandler/import xbeast.app.util.ErrorLogHandler/;
		$s =~ s/import beast.app.util.MessageLogHandler/import xbeast.app.util.MessageLogHandler/;
		$s =~ s/import beast.app.util.Utils/import xbeast.app.util.Utils/;
		$s =~ s/import beast.app.util.Utils6/import xbeast.app.util.Utils6/;
		$s =~ s/import beast.app.util.Version/import xbeast.app.util.Version/;
		$s =~ s/import beast.app.util.WholeNumberField/import xbeast.app.util.WholeNumberField/;
		$s =~ s/import beast.util.InputType/import xbeast.util.InputType/;
		$s =~ s/import beast.util.JSONParser/import xbeast.util.JSONParser/;
		$s =~ s/import beast.util.JSONParserException/import xbeast.util.JSONParserException/;
		$s =~ s/import beast.util.JSONProducer/import xbeast.util.JSONProducer/;
		$s =~ s/import beast.util.Package/import xbeast.util.Package/;
		$s =~ s/import beast.util.PackageDependency/import xbeast.util.PackageDependency/;
		$s =~ s/import beast.util.PackageManager/import xbeast.util.PackageManager/;
		$s =~ s/import beast.util.PackageVersion/import xbeast.util.PackageVersion/;
		$s =~ s/import beast.util.XMLParser/import xbeast.util.XMLParser/;
		$s =~ s/import beast.util.XMLParserException/import xbeast.util.XMLParserException/;
		$s =~ s/import beast.util.XMLParserUtils/import xbeast.util.XMLParserUtils/;
		$s =~ s/import beast.util.XMLProducer/import xbeast.util.XMLProducer/;
			
		$s =~ s/import beast.util.AddOnManager/import xbeast.util.PackageManager/;


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
