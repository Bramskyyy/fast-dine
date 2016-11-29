<?php

/* master.twig */
class __TwigTemplate_dcdf1ab1df13aacecb28bd7085622e916ecbf8e2fb5e5f99df327f6cdf1ec579 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'pageContent' => array($this, 'block_pageContent'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<!DOCTYPE html>
<html>
\t<head>
\t\t<meta charset=\"utf-8\">
\t\t<title>";
        // line 5
        echo twig_escape_filter($this->env, (isset($context["pageTitle"]) ? $context["pageTitle"] : null), "html", null, true);
        echo "</title>
\t\t<link href=\"https://fonts.googleapis.com/css?family=Amatic+SC:400,700\" rel=\"stylesheet\">
\t\t<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">
\t\t<link rel=\"stylesheet\" href=\"css/master.css\" media=\"screen\">
\t</head>
\t<body class=\"clearfix\">
\t\t<section id=\"header\" class=\"clearfix\">
\t\t\t<nav>
\t\t\t\t<h1><a href=\"index.php\">FastDine</a></h1>
\t\t\t\t<ul id=\"mainNav\">
\t\t\t\t\t<li ";
        // line 15
        if (((isset($context["active"]) ? $context["active"] : null) == "home")) {
            echo "class=\"active\"";
        }
        echo "><a href=\"index.php\">Home</a></li>
\t\t\t\t\t<li ";
        // line 16
        if (((isset($context["active"]) ? $context["active"] : null) == "overview")) {
            echo "class=\"active\"";
        }
        echo "><a href=\"overview.php\">Restaurants</a></li>
\t\t\t\t</ul>
\t\t\t\t<ul id=\"secondaryNav\">
\t\t\t\t\t<li ";
        // line 19
        if (((isset($context["active"]) ? $context["active"] : null) == "login")) {
            echo "class=\"active\"";
        }
        echo "><a href=\"login.php\">Log in</a></li>
\t\t\t\t</ul>
\t\t\t</nav>
\t\t</section>

\t\t<section id=\"content\" class=\"clearfix\">
\t\t\t";
        // line 25
        $this->displayBlock('pageContent', $context, $blocks);
        // line 28
        echo "\t\t</section>
\t</body>
</html>
";
    }

    // line 25
    public function block_pageContent($context, array $blocks = array())
    {
        // line 26
        echo "
\t\t\t";
    }

    public function getTemplateName()
    {
        return "master.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  76 => 26,  73 => 25,  66 => 28,  64 => 25,  53 => 19,  45 => 16,  39 => 15,  26 => 5,  20 => 1,);
    }

    /** @deprecated since 1.27 (to be removed in 2.0). Use getSourceContext() instead */
    public function getSource()
    {
        @trigger_error('The '.__METHOD__.' method is deprecated since version 1.27 and will be removed in 2.0. Use getSourceContext() instead.', E_USER_DEPRECATED);

        return $this->getSourceContext()->getCode();
    }

    public function getSourceContext()
    {
        return new Twig_Source("", "master.twig", "C:\\wamp\\www\\fast-dine\\Code\\Client\\templates\\master.twig");
    }
}
