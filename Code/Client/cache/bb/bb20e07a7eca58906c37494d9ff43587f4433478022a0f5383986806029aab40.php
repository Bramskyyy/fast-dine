<?php

/* overview.twig */
class __TwigTemplate_9daa6d8f9c3aca987d6fddd3d6a9a1e2fd98a9399c7bb51940de2c3eff289400 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        // line 1
        $this->parent = $this->loadTemplate("master.twig", "overview.twig", 1);
        $this->blocks = array(
            'pageContent' => array($this, 'block_pageContent'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "master.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_pageContent($context, array $blocks = array())
    {
        // line 4
        echo "
<h2 class=\"above-card\">";
        // line 5
        echo twig_escape_filter($this->env, (isset($context["pageTitle"]) ? $context["pageTitle"] : null), "html", null, true);
        echo "</h2>

<div class=\"overview clearfix\">
\t<div class=\"container clearfix\">
\t\t<div class=\"filter\">
\t\t\t<form id=\"filterForm\" action=\"";
        // line 10
        echo twig_escape_filter($this->env, (isset($context["PHP_SELF"]) ? $context["PHP_SELF"] : null), "html", null, true);
        echo "\" method=\"post\">
\t\t\t\t<input type=\"text\" class=\"formControl\" id=\"search\" name=\"search\" placeholder=\"Search by place name\">
\t\t\t\t<select class=\"people formControl\" id=\"people\" name=\"people\">
\t\t\t\t\t<option value=\"1\">1 persoon</option>
\t\t\t\t\t<option value=\"2\">2 personen</option>
\t\t\t\t\t<option value=\"3\">3 personen</option>
\t\t\t\t\t<option value=\"4\">4 personen</option>
\t\t\t\t\t<option value=\"5\">5 personen</option>
\t\t\t\t\t<option value=\"6\">6 personen</option>
\t\t\t\t\t<option value=\"7\">7 personen</option>
\t\t\t\t\t<option value=\"8\">8 personen</option>
\t\t\t\t\t<option value=\"9\">9 personen</option>
\t\t\t\t\t<option value=\"10\">10 personen</option>
\t\t\t\t</select>
\t\t\t\t<input type=\"date\" id=\"date\" name=\"date\" class=\"formControl\" />
\t\t\t\t<input type=\"time\" id=\"time\" name=\"time\" step=\"1800\" class=\"formControl\" />
\t\t\t\t<input type=\"submit\" name=\"name\" value=\"Filter\" />
\t\t\t</form>
\t\t</div>
\t\t<div class=\"restaurants clearfix\">
\t\t\t<div class=\"restaurant\">
\t\t\t\t<img src=\"img/souplounge.jpg\" alt=\"\" />
\t\t\t\t<h3><a href=\"#\">Souplounge</a></h3>
\t\t\t\t<div class=\"description\">
\t\t\t\t\t<p>
\t\t\t\t\t\tSouplounge is een gezellige eetgelegenheid in historisch centrum van Gent.
\t\t\t\t\t</p>
\t\t\t\t</div>
\t\t\t\t<a href=\"#\" class=\"book\">Reserveer</a>
\t\t\t</div>

\t\t\t<div class=\"restaurant\">
\t\t\t\t<img src=\"img/gust.jpg\" alt=\"\" />
\t\t\t\t<h3><a href=\"#\">Gust</a></h3>
\t\t\t\t<div class=\"description\">
\t\t\t\t\t<p>
\t\t\t\t\t\tOntbijt, lunch, take-away, brunch
\t\t\t\t\t</p>
\t\t\t\t</div>
\t\t\t\t<a href=\"#\" class=\"book\">Reserveer</a>
\t\t\t</div>

\t\t\t<div class=\"restaurant\">
\t\t\t\t<img src=\"img/tastyworld.jpg\" alt=\"\" />
\t\t\t\t<h3><a href=\"#\">Tasty World</a></h3>
\t\t\t\t<div class=\"description\">
\t\t\t\t\t<p>
\t\t\t\t\t\tTasty World is nieuw in zijn concept en biedt een gevarieerde en rijke keuze aan Veggieburgers en Smoothies in een magisch decor.
\t\t\t\t\t</p>
\t\t\t\t</div>
\t\t\t\t<a href=\"#\" class=\"book\">Reserveer</a>
\t\t\t</div>
\t\t</div>
\t</div>
</div>

";
    }

    public function getTemplateName()
    {
        return "overview.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  42 => 10,  34 => 5,  31 => 4,  28 => 3,  11 => 1,);
    }

    /** @deprecated since 1.27 (to be removed in 2.0). Use getSourceContext() instead */
    public function getSource()
    {
        @trigger_error('The '.__METHOD__.' method is deprecated since version 1.27 and will be removed in 2.0. Use getSourceContext() instead.', E_USER_DEPRECATED);

        return $this->getSourceContext()->getCode();
    }

    public function getSourceContext()
    {
        return new Twig_Source("", "overview.twig", "C:\\wamp\\www\\fast-dine\\Code\\Client\\templates\\overview.twig");
    }
}
