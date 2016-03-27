package org.semanticweb.elk.reasoner.tracing;

import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedClass;
import org.semanticweb.elk.reasoner.indexing.model.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.model.IndexedClassExpressionList;
import org.semanticweb.elk.reasoner.indexing.model.IndexedContextRoot;
import org.semanticweb.elk.reasoner.indexing.model.IndexedDeclarationAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedDefinitionAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedDisjointClassesAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedEntity;
import org.semanticweb.elk.reasoner.indexing.model.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.model.IndexedObjectPropertyRangeAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedPropertyChain;
import org.semanticweb.elk.reasoner.indexing.model.IndexedSubClassOfAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedSubObjectPropertyOfAxiom;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.ClassInconsistency;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.ContextInitialization;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.DisjointSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.Propagation;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.PropertyRange;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusionComposed;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusionDecomposed;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubContextInitialization;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubPropertyChain;

public class ConclusionDelegatingFactory implements Conclusion.Factory {

	private final Conclusion.Factory factory_;

	public ConclusionDelegatingFactory(Conclusion.Factory factory) {
		this.factory_ = factory;
	}

	@SuppressWarnings("static-method")
	protected <C extends Conclusion> C filter(C candidate) {
		// could be overridden in sub-classes
		return candidate;
	}

	@Override
	public ClassInconsistency getContradiction(IndexedContextRoot destination) {
		return filter(factory_.getContradiction(destination));
	}

	@Override
	public ContextInitialization getContextInitialization(
			IndexedContextRoot root) {
		return filter(factory_.getContextInitialization(root));
	}

	@Override
	public PropertyRange getPropertyRange(IndexedObjectProperty property,
			IndexedClassExpression range) {
		return filter(factory_.getPropertyRange(property, range));
	}

	@Override
	public SubClassInclusionComposed getSubClassInclusionComposed(
			IndexedContextRoot destination, IndexedClassExpression subsumer) {
		return filter(
				factory_.getSubClassInclusionComposed(destination, subsumer));
	}

	@Override
	public SubClassInclusionDecomposed getSubClassInclusionDecomposed(
			IndexedContextRoot destination, IndexedClassExpression subsumer) {
		return filter(
				factory_.getSubClassInclusionDecomposed(destination, subsumer));
	}

	@Override
	public IndexedDefinitionAxiom getIndexedDefinitionAxiom(
			ElkAxiom originalAxiom, IndexedClass definedClass,
			IndexedClassExpression definition) {
		return filter(factory_.getIndexedDefinitionAxiom(originalAxiom,
				definedClass, definition));
	}

	@Override
	public IndexedDisjointClassesAxiom getIndexedDisjointClassesAxiom(
			ElkAxiom originalAxiom, IndexedClassExpressionList members) {
		return filter(factory_.getIndexedDisjointClassesAxiom(originalAxiom,
				members));
	}

	@Override
	public IndexedDeclarationAxiom getIndexedDeclarationAxiom(
			ElkAxiom originalAxiom, IndexedEntity entity) {
		return filter(
				factory_.getIndexedDeclarationAxiom(originalAxiom, entity));
	}

	@Override
	public SubContextInitialization getSubContextInitialization(
			IndexedContextRoot root, IndexedObjectProperty subRoot) {
		return filter(factory_.getSubContextInitialization(root, subRoot));
	}

	@Override
	public IndexedSubClassOfAxiom getIndexedSubClassOfAxiom(
			ElkAxiom originalAxiom, IndexedClassExpression subClass,
			IndexedClassExpression superClass) {
		return filter(factory_.getIndexedSubClassOfAxiom(originalAxiom,
				subClass, superClass));
	}

	@Override
	public BackwardLink getBackwardLink(IndexedContextRoot destination,
			IndexedObjectProperty relation, IndexedContextRoot source) {
		return filter(factory_.getBackwardLink(destination, relation, source));
	}

	@Override
	public ForwardLink getForwardLink(IndexedContextRoot destination,
			IndexedPropertyChain relation, IndexedContextRoot target) {
		return filter(factory_.getForwardLink(destination, relation, target));
	}

	@Override
	public IndexedObjectPropertyRangeAxiom getIndexedObjectPropertyRangeAxiom(
			ElkAxiom originalAxiom, IndexedObjectProperty property,
			IndexedClassExpression range) {
		return filter(factory_.getIndexedObjectPropertyRangeAxiom(originalAxiom,
				property, range));
	}

	@Override
	public Propagation getPropagation(IndexedContextRoot destination,
			IndexedObjectProperty relation, IndexedObjectSomeValuesFrom carry) {
		return filter(factory_.getPropagation(destination, relation, carry));
	}

	@Override
	public IndexedSubObjectPropertyOfAxiom getIndexedSubObjectPropertyOfAxiom(
			ElkAxiom originalAxiom, IndexedPropertyChain subPropertyChain,
			IndexedObjectProperty superProperty) {
		return filter(factory_.getIndexedSubObjectPropertyOfAxiom(originalAxiom,
				subPropertyChain, superProperty));
	}

	@Override
	public SubPropertyChain getSubPropertyChain(IndexedPropertyChain subChain,
			IndexedPropertyChain superChain) {
		return filter(factory_.getSubPropertyChain(subChain, superChain));
	}

	@Override
	public DisjointSubsumer getDisjointSubsumer(IndexedContextRoot root,
			IndexedClassExpressionList disjointExpressions, int position,
			ElkAxiom reason) {
		return filter(factory_.getDisjointSubsumer(root, disjointExpressions,
				position, reason));
	}

}